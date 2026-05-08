package com.loveu.loveu.service;

import com.loveu.loveu.dto.ReporteDTO;
import com.loveu.loveu.model.EstadoReporte;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.model.Reporte;
import com.loveu.loveu.repository.PerfilRepository;
import com.loveu.loveu.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService {
    private static final Logger log = LoggerFactory.getLogger(ReporteService.class);

    private final ReporteRepository reporteRepository;
    private final PerfilRepository perfilRepository;

    public ReporteDTO crearReporte(Integer perfilReportanteId, Integer perfilReportadoId, String razonReporte) {
        log.info("Creando reporte: reportante={} reportado={} razon={}", perfilReportanteId, perfilReportadoId, razonReporte);

        if (perfilReportanteId.equals(perfilReportadoId)) {
            throw new RuntimeException("No puedes reportarte a ti mismo");
        }

        // Verificar si ya existe un reporte activo entre estos perfiles
        boolean yaReportado = reporteRepository.findByPerfilReportadoId(perfilReportadoId)
            .stream()
            .anyMatch(r -> r.getPerfilReportante().getId().equals(perfilReportanteId) && r.isActivo());

        if (yaReportado) {
            throw new RuntimeException("Ya has reportado a este perfil anteriormente");
        }

        Perfil perfilReportante = perfilRepository.findById(perfilReportanteId)
            .orElseThrow(() -> new RuntimeException("Perfil reportante no encontrado: " + perfilReportanteId));

        Perfil perfilReportado = perfilRepository.findById(perfilReportadoId)
            .orElseThrow(() -> new RuntimeException("Perfil reportado no encontrado: " + perfilReportadoId));

        Reporte reporte = Reporte.builder()
            .perfilReportante(perfilReportante)
            .perfilReportado(perfilReportado)
            .razonReporte(razonReporte)
            // estadoReporte y fechaReporte se asignan automáticamente en @PrePersist
            .build();

        reporte = reporteRepository.save(reporte);
        log.info("Reporte id={} creado con estado EN_REVISION", reporte.getId());
        return toDTO(reporte);
    }

    public List<ReporteDTO> getTodos() {
        log.info("Obteniendo todos los reportes");
        return reporteRepository.findAll()
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteDTO> getPorEstado(EstadoReporte estadoReporte) {
        log.info("Obteniendo reportes con estado={}", estadoReporte);
        return reporteRepository.findByEstadoReporte(estadoReporte)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ReporteDTO> getPorPerfilReportado(Integer perfilReportadoId) {
        log.info("Obteniendo reportes del perfilReportadoId={}", perfilReportadoId);
        return reporteRepository.findByPerfilReportadoId(perfilReportadoId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ReporteDTO actualizarEstado(Integer reporteId, EstadoReporte nuevoEstado) {
        log.info("Actualizando reporte id={} a estado={}", reporteId, nuevoEstado);
        Reporte reporte = reporteRepository.findById(reporteId)
            .orElseThrow(() -> new RuntimeException("Reporte no encontrado: " + reporteId));
        reporte.setEstadoReporte(nuevoEstado);
        reporte = reporteRepository.save(reporte);
        log.info("Reporte id={} actualizado a {}", reporte.getId(), reporte.getEstadoReporte());
        return toDTO(reporte);
    }

    public void eliminarReporte(Integer reporteId) {
        log.info("Eliminando reporte id={}", reporteId);
        reporteRepository.deleteById(reporteId);
    }

    private ReporteDTO toDTO(Reporte r) {
        return ReporteDTO.builder()
            .id(r.getId())
            .perfilReportante(r.getPerfilReportante().getId())
            .perfilReportado(r.getPerfilReportado().getId())
            .razonReporte(r.getRazonReporte())
            .estadoReporte(r.getEstadoReporte())
            .fechaReporte(r.getFechaReporte())
            .activo(r.isActivo())
            .build();
    }
}