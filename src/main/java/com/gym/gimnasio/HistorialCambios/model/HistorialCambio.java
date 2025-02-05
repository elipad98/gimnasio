package com.gym.gimnasio.HistorialCambios.model;

import com.gym.gimnasio.Miembro.model.MiembroDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class HistorialCambio {


    private Long historialId;
    private MiembroDTO miembro;
    private String tipoCambio;  // 'renovación', 'cancelación', 'cambio de plan'
    private LocalDateTime fechaCambio = LocalDateTime.now();
    private String descripcion;
    private String usuarioCambio;
}
