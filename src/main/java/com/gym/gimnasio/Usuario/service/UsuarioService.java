package com.gym.gimnasio.Usuario.service;

import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import com.gym.gimnasio.Usuario.entity.Usuario;
import com.gym.gimnasio.Usuario.model.UsuarioDTO;
import com.gym.gimnasio.Usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        if (usuarioRepository.existsByEmail(request.getEmail())) {
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
        miembro.setDireccion(request.getDireccion());
        miembro.setFechaRegistro(request.getFechaRegistro() != null ? request.getFechaRegistro() : java.time.LocalDate.now());
        miembro.setEstado(request.getEstado() != null ? request.getEstado() : true);
        miembro.setNotas(request.getNotas());
        miembro.setSexo(request.getSexo());
        miembro = miembroRepository.save(miembro);

        usuario.setMiembro(miembro);
        return usuarioRepository.save(usuario);
    }

    public ResponseEntity<String> subirFoto(Long id, MultipartFile foto) throws IOException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Directorio donde se guardarán las fotos
        String uploadDir = "src/main/resources/static/uploads/"; // Carpeta accesible desde el frontend
        String fileName = id + "_" + foto.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        // Crear el directorio si no existe
        Files.createDirectories(filePath.getParent());

        // Guardar la foto en el sistema de archivos
        Files.write(filePath, foto.getBytes());

        // Guardar la ruta relativa en la base de datos
        String relativePath = "/uploads/" + fileName; // Ruta relativa para acceder desde el frontend
        usuario.setFotoPerfil(relativePath);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(relativePath);
    }

}
