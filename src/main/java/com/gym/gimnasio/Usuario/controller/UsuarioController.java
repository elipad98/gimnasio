package com.gym.gimnasio.Usuario.controller;

import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Usuario.entity.Usuario;
import com.gym.gimnasio.Usuario.model.Rol;
import com.gym.gimnasio.Usuario.model.UsuarioDTO;
import com.gym.gimnasio.Usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Crear un nuevo usuario
    @PostMapping("/registrar")
    public Usuario registrarUsuario(@RequestBody UsuarioDTO usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/{id}/foto")
    public ResponseEntity<String> subirFoto(
            @PathVariable Long id,
            @RequestParam("foto") MultipartFile foto) throws IOException {

        return usuarioService.subirFoto(id,foto);
    }
}
