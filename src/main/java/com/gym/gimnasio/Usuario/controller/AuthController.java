package com.gym.gimnasio.Usuario.controller;

import com.gym.gimnasio.JwtUtil;
import com.gym.gimnasio.Usuario.model.AuthenticationRequest;
import com.gym.gimnasio.Usuario.model.AuthenticationResponse;
import com.gym.gimnasio.Usuario.service.CustomUserDetailsService;
import com.gym.gimnasio.Usuario.service.UsuarioService;
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


}
