package com.gym.gimnasio.Asistencia.model;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AsistenciaDTO {
    private Long asistenciaId;
    private Long miembroId;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private Integer duracionMinutos;
    private String notas;
}
