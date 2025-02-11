package com.gym.gimnasio.HistorialCambios.model;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class HistorialCambiosDTO {

    private Long historialId;
    private Long miembroId;
    private String tipoCambio;
    private LocalDateTime fechaCambio;
    private String descripcion;
    private String usuarioCambio;
}
