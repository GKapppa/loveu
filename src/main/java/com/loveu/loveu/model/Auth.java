

package com.loveu.loveu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Auth {
    // Auth almacena las credenciales de acceso y el rol asociado a un Usuario.
    // Este modelo representa el login/seguridad basica: email, password, rol y usuario relacionado. 
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    @Column(nullable=false, length=30)
    private String rol;  //Creamos una variable "rol" por si es un ADMIN o Usuario

    @Column(nullable=false)
    private Boolean activo = true;

    @OneToOne
    @JoinColumn(name= "usuario_id", referencedColumnName="id")
    private Usuario usuario;
}
