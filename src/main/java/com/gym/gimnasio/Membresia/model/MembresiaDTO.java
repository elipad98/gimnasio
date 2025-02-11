package com.gym.gimnasio.Membresia.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MembresiaDTO {

    private Long membresiaId;
    private Long miembroId;
    private Long tipoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal montoPagado;
    private String metodoPago;
    private String numeroFactura;
    private Boolean estadoPago;
    private LocalDateTime fechaPago;
    private String notas;


}
