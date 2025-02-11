package com.gym.gimnasio.TiposMembresia.model;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TipoMembresiaDTO {

    private Long tipoId;
    private String nombre;
    private Integer duracionDias;
    private BigDecimal precio;
    private String descripcion;
    private Boolean estado = true;
}
