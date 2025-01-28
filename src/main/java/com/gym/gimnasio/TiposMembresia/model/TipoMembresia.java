package com.gym.gimnasio.TiposMembresia.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TipoMembresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipoId;

    private String nombre;
    private Integer duracionDias;
    private BigDecimal precio;
    private String descripcion;
    private Boolean estado = true;
}
