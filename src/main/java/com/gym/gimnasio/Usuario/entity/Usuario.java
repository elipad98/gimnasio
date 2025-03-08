package com.gym.gimnasio.Usuario.entity;

import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Usuario.model.Rol;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "username", unique = true, length = 50)
    private String username;

    @Column(name = "password", length = 100) // Contraseña encriptada
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", length = 20)
    private Rol rol; // Enum para el rol (MIEMBRO, ADMIN)

    @OneToOne
    @JoinColumn(name = "miembro_id", referencedColumnName = "miembro_id")
    private Miembro miembro; // Relación con la tabla de miembros
}
