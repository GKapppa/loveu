package com.interaccion.interaccion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
