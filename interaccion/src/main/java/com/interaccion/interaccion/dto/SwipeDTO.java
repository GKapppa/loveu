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
public class SwipeDTO {
    private Integer id;
    private Integer perfilOrigenId;
    private Integer perfilDestinoId;
    private String decision;
    private LocalDateTime fecha;
}
