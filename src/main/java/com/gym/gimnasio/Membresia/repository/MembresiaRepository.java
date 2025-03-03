package com.gym.gimnasio.Membresia.repository;

import com.gym.gimnasio.Membresia.entity.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MembresiaRepository extends JpaRepository<Membresia, Long> {

    @Query("SELECT m.tipoMembresia.tipoId, COUNT(m) FROM Membresia m " +
            "WHERE YEAR(m.fechaInicio) = :anio AND MONTH(m.fechaInicio) = :mes " +
            "GROUP BY m.tipoMembresia.tipoId")
    List<Object[]> findDistribucionPorTipoYMes(@Param("anio") int anio, @Param("mes") int mes);

    @Query("SELECT tm.nombre, COUNT(m) FROM Membresia m JOIN m.tipoMembresia tm GROUP BY tm.nombre")
    List<Object[]> countByTipoMembresia();
}
