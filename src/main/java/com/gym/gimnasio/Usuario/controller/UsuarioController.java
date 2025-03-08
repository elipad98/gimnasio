package com.gym.gimnasio.Usuario.controller;

import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Usuario.entity.Usuario;
import com.gym.gimnasio.Usuario.model.Rol;
import com.gym.gimnasio.Usuario.model.UsuarioDTO;
import com.gym.gimnasio.Usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Crear un nuevo usuario
    @PostMapping("/registrar")
    public Usuario registrarUsuario(@RequestBody UsuarioDTO usuario) {
        return usuarioService.registrarUsuario(usuario);
    }
}
