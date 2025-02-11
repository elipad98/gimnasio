package com.gym.gimnasio.HistorialCambios.entity;

import com.gym.gimnasio.Miembro.entity.Miembro;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historialcambios")
public class HistorialCambios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historial_id")
    private Long historialId;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    @Column(name = "tipo_cambio", nullable = false, length = 50)
    private String tipoCambio;

    @Column(name = "fecha_cambio", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCambio;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "usuario_cambio", nullable = false, length = 50)
    private String usuarioCambio;
}
