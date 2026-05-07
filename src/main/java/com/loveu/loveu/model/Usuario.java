package com.loveu.loveu.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
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

    // Usuario almacena los datos personales de la cuenta, separados de las credenciales de AUTH
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio!")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    @Column(name="primer_nombre", nullable = false, length = 50)
    private String primerNombre;

    @NotBlank(message= "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    @Column(name="primer_apellido", nullable = false, length = 50)
    private String primerApellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @Past(message="La fecha de nacimiento debe ser anterior a la fecha actual.") // Usamos Past para evitar que la persona ingrese una fecha invalida por ejemplo annio 2030 y estamos al 2026 por ende es de tipo LocalDate.
    @Column(name="fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Size(max = 20, message = "El telefono no puede superar los 20 caracteres") 
    @Column(name="telefono", length = 20)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;

}
