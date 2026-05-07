package com.loveu.loveu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="perfiles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre visible es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre visible debe tener entre 2 y 50 caracteres")
    @Column(name="nombre", nullable = false, length = 50)
    private String nombreVisible;

    @NotBlank(message="La biografia es obligatoria")
    @Size(max=500, message="La biografia no debe superar los 500 caracteres")
    @Column(name="biografia",nullable=false, length=500)
    private String biografia;

    @Size(max = 80, message = "La ocupación no puede superar los 80 caracteres")
    @Column(name="ocupacion", length = 80, nullable=false)
    private String ocupacion;

    @Min(value = 100, message = "La altura mínima permitida es 100 cm")
    @Max(value = 250, message = "La altura máxima permitida es 250 cm")
    private Integer alturaCm;

    @Column(nullable = false)
    private Boolean activo = true;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable=false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="comuna_id", referencedColumnName="id", nullable=false)
    private Comuna comuna;
}
