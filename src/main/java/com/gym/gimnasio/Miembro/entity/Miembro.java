package com.gym.gimnasio.Miembro.entity;

import com.gym.gimnasio.Miembro.model.Sexo;
import com.gym.gimnasio.Usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "miembros")
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "miembro_id")
    private Long miembroId;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDate fechaRegistro = LocalDate.now();

    @Column(name = "estado")
    private Boolean estado = true;

    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", length = 20)
    private Sexo sexo;

    @OneToOne(mappedBy = "miembro")
    private Usuario usuario; // Relación con la tabla de usuarios
}
