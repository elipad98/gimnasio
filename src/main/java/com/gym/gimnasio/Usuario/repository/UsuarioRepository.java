package com.gym.gimnasio.Usuario.repository;

import com.gym.gimnasio.Usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    // Método opcional para verificar si el username ya existe
    boolean existsByUsername(String username);

    Optional<Usuario> findByUsername(String username);
}
