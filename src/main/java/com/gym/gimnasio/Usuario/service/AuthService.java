package com.gym.gimnasio.Usuario.service;

import com.gym.gimnasio.Usuario.entity.PasswordResetToken;
import com.gym.gimnasio.Usuario.entity.Usuario;
import com.gym.gimnasio.Usuario.repository.PasswordResetTokenRepository;
import com.gym.gimnasio.Usuario.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordResetTokenRepository tokenRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public static class AuthResult {
        private final String message;
        private final boolean success;

        public AuthResult(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public AuthResult requestPasswordReset(String email) {
        try {
            Usuario user = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiresAt(LocalDateTime.now().plusHours(1));

            tokenRepository.save(resetToken);

            emailService.sendResetPasswordEmail(user.getEmail(), token);

            return new AuthResult("Correo de restablecimiento enviado.", true);
        } catch (MessagingException e) {
            return new AuthResult("Error al enviar el correo.", false);
        } catch (RuntimeException e) {
            return new AuthResult(e.getMessage(), false);
        }
    }

    public AuthResult resetPassword(String token, String newPassword) {
        try {
            PasswordResetToken resetToken = tokenRepository.findByToken(token)
                    .orElseThrow(() -> new RuntimeException("Token inválido"));

            if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
                tokenRepository.delete(resetToken);
                throw new RuntimeException("El token ha expirado.");
            }

            Usuario user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            usuarioRepository.save(user);

            tokenRepository.delete(resetToken);

            return new AuthResult("Contraseña restablecida exitosamente.", true);
        } catch (RuntimeException e) {
            return new AuthResult(e.getMessage(), false);
        }
    }

}
