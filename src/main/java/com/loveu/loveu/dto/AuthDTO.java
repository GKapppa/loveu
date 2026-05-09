package com.loveu.loveu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthDTO {
    private Integer id;
    private String email;
    // Decidimos no incluir password en el DTO de respuesta para evitar exponer Informacion sensible.
    private String rol;
    private boolean activo;
    private Integer usuarioId;
}
