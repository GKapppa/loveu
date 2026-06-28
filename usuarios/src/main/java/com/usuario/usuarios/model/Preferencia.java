package com.usuario.usuarios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "preferencia")
@Builder
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El id de perfil no puede estar vacio")
    private Integer perfilId;

    @NotNull(message = "La edad minima es obligatoria")
    @Min(value = 18, message = "La edad minima debe ser al menos 18")
    @Max(value = 99, message = "La edad minima no puede superar 99")
    private Integer edadMin;

    @NotNull(message = "La edad maxima es obligatoria")
    @Min(value = 18, message = "La edad maxima debe ser al menos 18")
    @Max(value = 99, message = "La edad maxima no puede superar 99")
    private Integer edadMax;

    @NotNull(message = "La distancia maxima es obligatoria")
    @Min(value = 1, message = "La distancia maxima debe ser al menos 1 km")
    @Max(value = 500, message = "La distancia maxima no puede superar 500 km")
    private Integer distanciaMaxKm;

    @Min(value = 100, message = "La altura minima permitida es 100 cm")
    @Max(value = 250, message = "La altura minima no puede superar 250 cm")
    private Integer alturaMinCm;

    @Min(value = 100, message = "La altura maxima permitida es 100 cm")
    @Max(value = 250, message = "La altura maxima no puede superar 250 cm")
    private Integer alturaMaxCm;

    @NotBlank(message = "El genero preferido es obligatorio")
    @Size(max = 20, message = "El genero preferido no puede superar 20 caracteres")
    private String generoPreferido;

    @Builder.Default
    private boolean activo = true;
}
