package com.loveu.loveu.dto;

import java.time.LocalDateTime;

import com.loveu.loveu.model.EstadoReporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteDTO {
    private Integer id;
    private Integer perfilReportanteId;
    private Integer perfilReportadoId;
    private String razonReporte;
    private EstadoReporte estadoReporte;
    private LocalDateTime fechaReporte;
    private boolean activo;
}
