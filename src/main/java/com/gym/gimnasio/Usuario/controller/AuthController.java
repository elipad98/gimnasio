package com.gym.gimnasio.Usuario.controller;

import com.gym.gimnasio.JwtUtil;
import com.gym.gimnasio.Usuario.model.AuthenticationRequest;
import com.gym.gimnasio.Usuario.model.AuthenticationResponse;
import com.gym.gimnasio.Usuario.service.AuthService;
import com.gym.gimnasio.Usuario.service.CustomUserDetailsService;
import com.gym.gimnasio.Usuario.service.UsuarioService;
import jakarta.mail.MessagingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUsuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }

        UserDetails userDetails = customUsuarioService.loadUserByUsername(request.getUsername());
        String rol = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .findFirst()
                .orElse("");
        String jwt = jwtUtil.generateToken(userDetails.getUsername(), rol);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<Response> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        AuthService.AuthResult result = authService.requestPasswordReset(request.getEmail());
        Response response = new Response(result.getMessage(), result.isSuccess());
        if (result.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordRequest request) {
        AuthService.AuthResult result = authService.resetPassword(request.getToken(), request.getPassword());
        Response response = new Response(result.getMessage(), result.isSuccess());
        if (result.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }
}

class ForgotPasswordRequest {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class ResetPasswordRequest {
    private String token;
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Response {
    private String message;
    private boolean success;

    public Response(String message, boolean success) {
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

