package com.loveu.loveu.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio!")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String primerNombre;

    @NotBlank(message= "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String primerApellido;

    @NotBlank(message= "El email es obligatorio.")
    @Email(message = "El email debe tener un formato valido.")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @Past(message="La fecha de nacimiento debe ser anterior a la fecha actual.") // Usamos Past para evitar que la persona ingrese una fecha invalida por ejemplo annio 2030 y estamos al 2026 por ende es de tipo LocalDate.
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Size(max = 20, message = "El telefono no puede superar los 20 caracteres") 
    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;

}
