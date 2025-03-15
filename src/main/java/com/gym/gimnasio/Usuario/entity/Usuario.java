package com.gym.gimnasio.Usuario.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Usuario.model.Rol;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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

    @Column(name = "password", length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", length = 20)
    private Rol rol;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "miembro_id", referencedColumnName = "miembro_id")
    @JsonManagedReference
    @ToString.Exclude // Excluye este campo del toString()
    private Miembro miembro;

    @Column(name = "foto_perfil") // Ruta de la foto en el sistema de archivos
    private String fotoPerfil; // Ejemplo: "uploads/1_profile.jpg"

}
