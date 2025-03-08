package com.gym.gimnasio.Miembro.repository;

import com.gym.gimnasio.Miembro.entity.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    long countByEstado(boolean estado);

    //Checar si ya existe el miembro usando el email
    boolean existsByEmail(String email);

    //Contar por sexo
    @Query("SELECT m.sexo, COUNT(m) as cantidad FROM Miembro m GROUP BY m.sexo")
    List<Object[]> contarPorSexo();
}
