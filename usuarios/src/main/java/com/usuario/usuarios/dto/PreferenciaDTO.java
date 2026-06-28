package com.usuario.usuarios.dto;

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
    private Integer edadMin;
    private Integer edadMax;
    private Integer distanciaMaxKm;
    private Integer alturaMinCm;
    private Integer alturaMaxCm;
    private String generoPreferido;
}
