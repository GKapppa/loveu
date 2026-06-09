package com.loveu.loveu.dto;

import java.time.LocalDateTime;

import com.loveu.loveu.model.DecisionSwipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwipeDTO {
    private Integer perfilOrigenId;
    private Integer perfilDestinoId;
    private DecisionSwipe decision;
    private LocalDateTime fecha;
}
