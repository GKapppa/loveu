package com.interaccion.interaccion.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeDTO {
    private Integer id;
    private Integer matchId;
    private Integer perfilEmisorId;
    private Integer perfilReceptorId;
    private String contenido;
    private LocalDateTime sentAt;
    private boolean leido;
}
