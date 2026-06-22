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
public class NotificacionDTO {
    private Integer id;
    private Integer perfilDestinatarioId;
    private String type;
    private String message;
    private boolean leido;
    private LocalDateTime createdAt;
}
