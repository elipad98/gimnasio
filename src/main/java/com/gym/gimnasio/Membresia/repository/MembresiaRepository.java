package com.gym.gimnasio.Membresia.repository;

import com.gym.gimnasio.Membresia.entity.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MembresiaRepository extends JpaRepository<Membresia, Long> {

    @Query("SELECT m.tipoMembresia.tipoId, COUNT(m) FROM Membresia m " +
            "WHERE YEAR(m.fechaInicio) = :anio AND MONTH(m.fechaInicio) = :mes " +
            "GROUP BY m.tipoMembresia.tipoId")
    List<Object[]> findDistribucionPorTipoYMes(@Param("anio") int anio, @Param("mes") int mes);

    @Query("SELECT tm.nombre, COUNT(m) FROM Membresia m JOIN m.tipoMembresia tm GROUP BY tm.nombre")
    List<Object[]> countByTipoMembresia();

    @Query("SELECT COUNT(m) FROM Membresia m WHERE m.fechaFin BETWEEN :startDate AND :endDate AND m.estadoPago = true")
    Long countMembresiasProximasAVencer(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(m.montoPagado), 0) FROM Membresia m " +
            "WHERE m.estadoPago = true " +
            "AND m.fechaPago >= :inicioMes " +
            "AND m.fechaPago < :finMes")
    BigDecimal sumIngresosMesActual(LocalDateTime inicioMes, LocalDateTime finMes);

    @Query(value = "SELECT " +
            "mem.membresia_id, " +
            "m.nombre, " +
            "m.apellido, " +
            "tm.nombre AS tipo_membresia, " +
            "mem.fecha_inicio, " +
            "mem.fecha_fin, " +
            "mem.monto_pagado, " +
            "CASE WHEN mem.estado_pago = TRUE THEN 'Pagado' ELSE 'Pendiente' END AS estado_pago " +
            "FROM membresias mem " +
            "JOIN miembros m ON mem.miembro_id = m.miembro_id " +
            "JOIN tiposmembresia tm ON mem.tipo_id = tm.tipo_id " +
            "WHERE mem.fecha_fin BETWEEN CURDATE() AND CURDATE() + INTERVAL 7 DAY " +
            "AND tm.estado = TRUE", nativeQuery = true)
    List<Object[]> obtenerMembresiasProximasAVencer();

}
