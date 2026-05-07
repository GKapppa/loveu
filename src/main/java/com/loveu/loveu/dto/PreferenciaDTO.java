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
    private Integer id;
    private Integer perfilId;
    private String generoDeseado;
    private Integer edadMinima;
    private Integer edadMaxima;
    private Integer distanciaMaximaKm;
    private boolean activo;
}
