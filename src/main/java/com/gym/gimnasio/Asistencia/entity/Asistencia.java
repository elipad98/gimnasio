package com.gym.gimnasio.Asistencia.entity;

import com.gym.gimnasio.Miembro.entity.Miembro;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "asistencia")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asistencia_id")
    private Long asistenciaId;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    @Column(name = "fecha_entrada", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;

    @PrePersist
    protected void onCreate() {
        if (fechaEntrada == null) {
            fechaEntrada = LocalDateTime.now();
        }
    }
}
