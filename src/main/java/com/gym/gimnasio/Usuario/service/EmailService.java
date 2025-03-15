package com.gym.gimnasio.Usuario.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetPasswordEmail(String to, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String resetLink = baseUrl + "/auth/reset?token=" + token;

        helper.setTo(to);
        helper.setSubject("Restablecer tu contraseña");
        helper.setText(
                "<p>Hola,</p>" +
                        "<p>Recibimos una solicitud para restablecer tu contraseña. Haz clic en el siguiente enlace:</p>" +
                        "<a href=\"" + resetLink + "\">" + resetLink + "</a>" +
                        "<p>Este enlace expira en 1 hora. Si no solicitaste esto, ignora este correo.</p>",
                true
        );

        mailSender.send(message);
    }
}
