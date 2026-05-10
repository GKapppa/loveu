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
public class ReporteDTO {
    private Integer perfilReportanteId;
    private Integer perfilReportadoId;
    private String razonReporte;
    private LocalDateTime fechaReporte;
}
