package com.usuario.usuarios.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FotoPerfilDTO {
    private Integer fotoId;
    private Integer perfilId;
    private String urlFoto;
    private boolean principal;
    private Integer orden;
    private LocalDateTime fechaSubida;
}