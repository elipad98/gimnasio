package com.gym.gimnasio.Miembro.model;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MiembroDTO {

    private Long miembroId;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;
    private String direccion;
    private LocalDate fechaRegistro = LocalDate.now();
    private Boolean estado = true;
    private String notas;
}
