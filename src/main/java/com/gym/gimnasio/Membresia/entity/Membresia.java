package com.gym.gimnasio.Membresia.entity;

import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.TiposMembresia.entity.TipoMembresia;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "membresias")
public class Membresia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membresia_id", nullable = false)
    private Long membresiaId;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoMembresia tipoMembresia;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "monto_pagado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;

    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;

    @Column(name = "numero_factura", length = 50)
    private String numeroFactura;

    @Column(name = "estado_pago", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean estadoPago = true;

    @Column(name = "fecha_pago", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaPago;


    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;

    @PrePersist
    protected void onCreate() {
        if (fechaPago == null) {
            fechaPago = LocalDateTime.now();
        }
    }

}
