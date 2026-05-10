package com.loveu.loveu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreferenciaDTO {
    private Integer perfilId;
    private String generoDeseado;
    private Integer edadMinima;
    private Integer edadMaxima;
    private Integer distanciaMaximaKm;

    public Integer getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Integer perfilId) {
        this.perfilId = perfilId;
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
}
