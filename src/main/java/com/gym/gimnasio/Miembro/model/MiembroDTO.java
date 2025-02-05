package com.gym.gimnasio.Miembro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class MiembroDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
