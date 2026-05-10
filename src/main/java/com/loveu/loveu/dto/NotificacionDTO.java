package com.loveu.loveu.dto;

import java.time.LocalDateTime;

import com.loveu.loveu.model.NotificacionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionDTO {
    private Integer perfilDestinatarioId;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
    private NotificacionType type;
}
