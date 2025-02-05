package com.gym.gimnasio.Asistencia.model;

import com.gym.gimnasio.Miembro.model.MiembroDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Asistencia {
    private Long asistenciaId;
    private MiembroDTO miembro;

    private LocalDateTime fechaEntrada = LocalDateTime.now();
    private LocalDateTime fechaSalida;
    private Integer duracionMinutos;
    private String notas;
}
