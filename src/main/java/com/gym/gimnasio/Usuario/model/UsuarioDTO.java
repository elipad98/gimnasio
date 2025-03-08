package com.gym.gimnasio.Usuario.model;

import com.gym.gimnasio.Miembro.model.Sexo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDTO {
    // Campos de Usuario
    private String username;
    private String password;
    private Rol rol;

    // Campos de Miembro
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;
    private String direccion;
    private LocalDate fechaRegistro; // Opcional, tiene valor por defecto
    private Boolean estado;          // Opcional, tiene valor por defecto
    private String notas;
    private Sexo sexo;
}
