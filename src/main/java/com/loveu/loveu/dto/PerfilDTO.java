package com.loveu.loveu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilDTO {
    private Integer id;
    private String nombreVisible;
    private String biografia;
    private Integer alturaCm;
    private boolean activo;
    private Integer usuarioId;
    private Integer comunaId;
    private String ocupacion;
}
