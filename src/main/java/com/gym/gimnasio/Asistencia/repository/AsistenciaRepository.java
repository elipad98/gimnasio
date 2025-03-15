package com.gym.gimnasio.Asistencia.repository;

import com.gym.gimnasio.Asistencia.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    // Obtener asistencias por día
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE DATE(a.fechaEntrada) = DATE(:fecha)")
    Long countAsistenciasPorDia(@Param("fecha") LocalDateTime fecha);

    // Obtener asistencias por semana
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE YEARWEEK(a.fechaEntrada) = YEARWEEK(:fecha)")
    Long countAsistenciasPorSemana(@Param("fecha") LocalDateTime fecha);

    // Obtener asistencias por mes
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE YEAR(a.fechaEntrada) = YEAR(:fecha) AND MONTH(a.fechaEntrada) = MONTH(:fecha)")
    Long countAsistenciasPorMes(@Param("fecha") LocalDateTime fecha);

    // Obtener asistencias por hora del día
    @Query("SELECT HOUR(a.fechaEntrada) AS hora, COUNT(a) AS cantidad FROM Asistencia a WHERE DATE(a.fechaEntrada) = DATE(:fecha) GROUP BY HOUR(a.fechaEntrada)")
    List<Object[]> countAsistenciasPorHora(@Param("fecha") LocalDateTime fecha);

    // Obtener asistencias por día de la semana
    @Query("SELECT DAYOFWEEK(a.fechaEntrada) AS dia, COUNT(a) AS cantidad FROM Asistencia a WHERE YEARWEEK(a.fechaEntrada) = YEARWEEK(:fecha) GROUP BY DAYOFWEEK(a.fechaEntrada)")
    List<Object[]> countAsistenciasPorDiaSemana(@Param("fecha") LocalDateTime fecha);

    // Obtener asistencias por mes del año
    @Query("SELECT MONTH(a.fechaEntrada) AS mes, COUNT(a) AS cantidad FROM Asistencia a WHERE YEAR(a.fechaEntrada) = YEAR(:fecha) GROUP BY MONTH(a.fechaEntrada)")
    List<Object[]> countAsistenciasPorMesdelAno(@Param("fecha") LocalDateTime fecha);

    @Query("SELECT COUNT(a) FROM Asistencia a " +
            "WHERE a.fechaEntrada >= :inicioDia " +
            "AND a.fechaEntrada < :finDia")
    Long countAsistenciasDiaActual(LocalDateTime inicioDia, LocalDateTime finDia);

    @Query(value = "SELECT " +
            "m.miembro_id, " +
            "m.nombre, " +
            "m.apellido, " +
            "COUNT(a.asistencia_id) AS total_asistencias " +
            "FROM asistencia a " +
            "JOIN miembros m ON a.miembro_id = m.miembro_id " +
            "GROUP BY m.miembro_id, m.nombre, m.apellido " +
            "ORDER BY total_asistencias DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> obtenerTopMiembrosFrecuentes();
}
