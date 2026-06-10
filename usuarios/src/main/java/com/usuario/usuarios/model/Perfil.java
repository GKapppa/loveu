package com.usuario.usuarios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "perfil")
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre visible es obligatorio")
    @Size(min = 10, max= 30, message = "El nombre visible debe tener entre 10 y 30 caracteres")
    private String nombreVisible;

    @NotBlank(message = "La biografia es obligatoria")
    @Size(max = 500, message = "La biografia no debe superar los 500 caracteres")
    private String biografia;

    @Size(max = 30, message = "La ocupacion no puede superar los 30 caracteres")
    private String ocupacion;

    @Min(value= 100, message= "La altura minima permitida es 100 cm")
    @Max(value= 250, message= "La altura maxima permitida es 250 cm")
    private Integer alturaCm;

    @Builder.Default
    private boolean activo = true;

    @NotNull(message = "El id de usuario no puede estar vacio")
    private Integer usuarioId;

    @NotNull(message = "El id de comuna no puede estar vacio")
    private Integer comunaId;
}
