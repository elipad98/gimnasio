package com.gym.gimnasio.Miembro.repository;

import com.gym.gimnasio.Miembro.entity.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiembroRepository extends JpaRepository<Miembro, Long> {
}
