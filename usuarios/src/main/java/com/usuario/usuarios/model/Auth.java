package com.usuario.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth")
@Builder
public class Auth {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message="El email es obligatorio")
    @Email(message="El email debe tener un formato valido")
    @Column(nullable= false, length = 100)
    private String email;

    @NotBlank(message="La contrasena es obligatoria")
    @Size(min = 6, max = 100, message="La contrasena debe tener entre 6 y 100 caracteres")
    @Column(nullable= false, length= 100)
    private String password;

    @NotBlank(message=" rol es obligatorio")
    @Column(nullable=false, length=15)
    private String rol;

    @Column(nullable=false, name = "usuarioid")
    private Integer usuarioId;

    @Builder.Default
    @Column(nullable=false)
    private boolean activo = true;

}
