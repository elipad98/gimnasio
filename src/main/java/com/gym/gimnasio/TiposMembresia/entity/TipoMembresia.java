package com.gym.gimnasio.TiposMembresia.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tiposmembresia")
public class TipoMembresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_id", nullable = false)
    private Long tipoId;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean estado = true;
}
