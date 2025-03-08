package com.gym.gimnasio.Usuario.service;

import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import com.gym.gimnasio.Usuario.entity.Usuario;
import com.gym.gimnasio.Usuario.model.UsuarioDTO;
import com.gym.gimnasio.Usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrarUsuario(UsuarioDTO request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El username ya está en uso");
        }
        if (miembroRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());
        usuario = usuarioRepository.save(usuario);

        Miembro miembro = new Miembro();
        miembro.setNombre(request.getNombre());
        miembro.setApellido(request.getApellido());
        miembro.setFechaNacimiento(request.getFechaNacimiento());
        miembro.setTelefono(request.getTelefono());
        miembro.setEmail(request.getEmail());
        miembro.setDireccion(request.getDireccion());
        miembro.setFechaRegistro(request.getFechaRegistro() != null ? request.getFechaRegistro() : java.time.LocalDate.now());
        miembro.setEstado(request.getEstado() != null ? request.getEstado() : true);
        miembro.setNotas(request.getNotas());
        miembro.setSexo(request.getSexo());
        miembro = miembroRepository.save(miembro);

        usuario.setMiembro(miembro);
        return usuarioRepository.save(usuario);
    }

}
