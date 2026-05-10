package com.loveu.loveu.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeDTO {
    private Integer perfilEmisorId;
    private Integer perfilReceptorId;
    private String contenido;
    private LocalDateTime sentAt;
    private boolean read;
}
