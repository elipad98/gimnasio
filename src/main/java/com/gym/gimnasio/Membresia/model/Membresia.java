package com.gym.gimnasio.Membresia.model;

import com.gym.gimnasio.Miembro.model.MiembroDTO;
import com.gym.gimnasio.TiposMembresia.model.TipoMembresia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membresiaId;

    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private MiembroDTO miembro;

    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoMembresia tipoMembresia;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal montoPagado;
    private String metodoPago;
    private String numeroFactura;
    private Boolean estadoPago = true;
    private LocalDateTime fechaPago = LocalDateTime.now();
    private String notas;
}
