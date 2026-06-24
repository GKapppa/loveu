package com.reporte.reporte.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reporte.reporte.dto.ReporteDTO;
import com.reporte.reporte.model.EstadoReporte;
import com.reporte.reporte.model.Reporte;
import com.reporte.reporte.repository.ReporteRepository;

@Service
public class ReporteService {

    private static final Logger log = LoggerFactory.getLogger(ReporteService.class);

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private ReporteValidaciones validaciones;

    public ReporteDTO crearReporte(Integer perfilReportanteId, Integer perfilReportadoId, String razonReporte) {
        log.info("[v2] Creando reporte: reportante={} reportado={} razon={}", perfilReportanteId, perfilReportadoId, razonReporte);
        validaciones.validarNoSelfReporte(perfilReportanteId, perfilReportadoId);
        validaciones.validarNoDuplicateReporte(perfilReportanteId, perfilReportadoId);

        Reporte reporte = Reporte.builder()
                .perfilReportante(perfilReportanteId)
                .perfilReportado(perfilReportadoId)
                .razonReporte(razonReporte)
                .build();

        reporte = reporteRepository.save(reporte);
        log.info("[v2] Reporte id={} creado con estado EN_REVISION", reporte.getId());
        return toDTO(reporte);
    }

    public List<ReporteDTO> getTodos() {
        log.info("[v2] Obteniendo todos los reportes");
        return reporteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteDTO> getPorEstado(EstadoReporte estadoReporte) {
        log.info("[v2] Obteniendo reportes con estado={}", estadoReporte);
        return reporteRepository.findByEstadoReporte(estadoReporte).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteDTO> getPorPerfilReportado(Integer perfilReportadoId) {
        log.info("[v2] Obteniendo reportes del perfilReportadoId={}", perfilReportadoId);
        return reporteRepository.findByPerfilReportado(perfilReportadoId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ReporteDTO actualizarEstado(Integer reporteId, EstadoReporte nuevoEstado) {
        log.info("[v2] Actualizando reporte id={} a estado={}", reporteId, nuevoEstado);
        Reporte reporte = validaciones.validarReporteExiste(reporteId);
        reporte.setEstadoReporte(nuevoEstado);
        reporte = reporteRepository.save(reporte);
        log.info("[v2] Reporte id={} actualizado a {}", reporte.getId(), reporte.getEstadoReporte());
        return toDTO(reporte);
    }

    public void eliminarReporte(Integer reporteId) {
        log.info("[v2] Eliminando reporte id={}", reporteId);
        validaciones.validarReporteExiste(reporteId);
        reporteRepository.deleteById(reporteId);
    }

    private ReporteDTO toDTO(Reporte r) {
        return ReporteDTO.builder()
                .perfilReportanteId(r.getPerfilReportante())
                .perfilReportadoId(r.getPerfilReportado())
                .razonReporte(r.getRazonReporte())
                .fechaReporte(r.getFechaReporte())
                .build();
    }
}
