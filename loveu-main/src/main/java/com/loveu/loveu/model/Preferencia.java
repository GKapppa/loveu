package com.loveu.loveu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Builder
@NoArgsConstructor
@Table(name="preferencias")
@Entity
public class Preferencia {
    // Preferencia defino los filtros de busqueda que tendra asociado un perfil.
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    // Esto hace que cada perfil_id tenga sus prefeerencias unicas por así decirlo.
    @OneToOne
    @JoinColumn(name="perfil_id", referencedColumnName="id", unique=true, nullable=false)
    private Perfil perfil;

    @NotBlank(message="El genero deseado no debe estar vacio!")
    @Column(name="genero_deseado", length=20, nullable=false)
    @Size(max=20, message="No exceda el maximo de 20 caracteres")
    private String generoDeseado;

    @NotNull(message="La edad minima no puede estar nulo!")
    @Column(name="edad_minima",nullable=false)
    @Min(value=18, message="La edad minima es 18!")
    private Integer edadMinima;
    // Esto se validara en el services pero ya tenemos mensaje de precaucion.
    @NotNull(message="La edad maxima no puede estar nula!")
    @Column(name="edad_maxima",nullable=false)
    @Min(value=18, message="La edad minima es 18!")
    @Max(value=99, message="La edad maxima es 99")
    private Integer edadMaxima;

    @NotNull(message="La distancia no puede estar nula!")
    @Column(name="distancia_maxima_km", nullable=false)
    @Min(value=1, message="La distancia minima debe ser al menos 1KM")
    @Max(value=200, message="La distancia maxima no puede superar los 200KM")
    private Integer distanciaMaximaKm;

    @Builder.Default
    @Column(nullable = false)
    private boolean activo = true;

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getGeneroDeseado() {
        return generoDeseado;
    }

    public void setGeneroDeseado(String generoDeseado) {
        this.generoDeseado = generoDeseado;
    }

    public Integer getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }

    public Integer getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(Integer edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public Integer getDistanciaMaximaKm() {
        return distanciaMaximaKm;
    }

    public void setDistanciaMaximaKm(Integer distanciaMaximaKm) {
        this.distanciaMaximaKm = distanciaMaximaKm;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
