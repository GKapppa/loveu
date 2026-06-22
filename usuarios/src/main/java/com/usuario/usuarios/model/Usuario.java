package com.usuario.usuarios.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio!")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String primerNombre;

    @NotBlank(message= "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String primerApellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @Past(message="La fecha de nacimiento debe ser anterior a la fecha actual.")
    private LocalDate fechaNacimiento;

    @Size(max = 20, message = "El telefono no puede superar los 20 caracteres")
    private String telefono;

    @Builder.Default
    private boolean activo = true;

}