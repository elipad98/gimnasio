package com.gym.gimnasio.Miembro.repository;

import com.gym.gimnasio.Miembro.entity.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    long countByEstado(boolean estado);



    //Contar por sexo
    @Query("SELECT m.sexo, COUNT(m) as cantidad FROM Miembro m GROUP BY m.sexo")
    List<Object[]> contarPorSexo();

    @Query("SELECT COUNT(m) FROM Miembro m WHERE m.estado = true")
    Long countMiembrosActivos();

    @Query("SELECT EXTRACT(MONTH FROM m.fechaRegistro) as month, COUNT(m) as count " +
            "FROM Miembro m " +
            "WHERE EXTRACT(YEAR FROM m.fechaRegistro) = :year " +
            "GROUP BY EXTRACT(MONTH FROM m.fechaRegistro) " +
            "ORDER BY month")
    List<Object[]> contarMiembrosPorMes(@Param("year") Integer year);

    @Query(value = "SELECT " +
            "CASE " +
            "  WHEN TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE()) BETWEEN 15 AND 20 THEN '15-20' " +
            "  WHEN TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE()) BETWEEN 21 AND 30 THEN '21-30' " +
            "  WHEN TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE()) BETWEEN 31 AND 40 THEN '31-40' " +
            "  WHEN TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE()) BETWEEN 41 AND 50 THEN '41-50' " +
            "  WHEN TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE()) > 50 THEN '50+' " +
            "END AS rango_edad, " +
            "COUNT(*) AS cantidad " +
            "FROM miembros " +
            "WHERE fecha_nacimiento IS NOT NULL " +
            "GROUP BY rango_edad " +
            "ORDER BY rango_edad", nativeQuery = true)
    List<Object[]> obtenerDistribucionEdades();
}
