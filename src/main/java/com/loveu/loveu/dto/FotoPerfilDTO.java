package com.loveu.loveu.dto;

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
    private Integer id;
    private Integer perfilId;
    private String urlFoto;
    private boolean principal;
    private Integer orden;
    private LocalDateTime fechaSubida;
    private boolean activo;
}
