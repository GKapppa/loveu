package com.loveu.loveu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.ReporteDTO;
import com.loveu.loveu.model.EstadoReporte;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.model.Reporte;
import com.loveu.loveu.repository.PerfilRepository;
import com.loveu.loveu.repository.ReporteRepository;

// @Service identifica esta clase como capa de reglas de negocio.
@Service
public class ReporteService {
    // Logger para registrar eventos importantes del servicio.
    private static final Logger log = LoggerFactory.getLogger(ReporteService.class);

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public ReporteDTO crearReporte(Integer perfilReportanteId, Integer perfilReportadoId, String razonReporte) {
        log.info("Creando reporte: reportante={} reportado={} razon={}", perfilReportanteId, perfilReportadoId, razonReporte);

        if (perfilReportanteId.equals(perfilReportadoId)) {
            throw new RuntimeException("No puedes reportarte a ti mismo");
        }

        // Verificar si ya existe un reporte activo entre estos perfiles
        boolean yaReportado = reporteRepository.findByPerfilReportadoId(perfilReportadoId)
            // stream permite revisar la lista sin usar un for manual.
            .stream()
            // anyMatch retorna true si encuentra un reporte activo del mismo reportante.
            .anyMatch(r -> r.getPerfilReportante().getId().equals(perfilReportanteId) && r.isActivo());

        if (yaReportado) {
            throw new RuntimeException("Ya has reportado a este perfil anteriormente");
        }

        // findById devuelve Optional; orElseThrow lanza error si el perfil no existe.
        Perfil perfilReportante = perfilRepository.findById(perfilReportanteId)
            .orElseThrow(() -> new RuntimeException("Perfil reportante no encontrado: " + perfilReportanteId));

        Perfil perfilReportado = perfilRepository.findById(perfilReportadoId)
            .orElseThrow(() -> new RuntimeException("Perfil reportado no encontrado: " + perfilReportadoId));

        // builder crea la entidad Reporte de forma ordenada.
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
            // map(this::toDTO) transforma cada entidad en un DTO.
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
        // Se cambia el estado sin borrar el reporte.
        reporte.setEstadoReporte(nuevoEstado);
        reporte = reporteRepository.save(reporte);
        log.info("Reporte id={} actualizado a {}", reporte.getId(), reporte.getEstadoReporte());
        return toDTO(reporte);
    }

    public void eliminarReporte(Integer reporteId) {
        log.info("Eliminando reporte id={}", reporteId);
        reporteRepository.deleteById(reporteId);
    }

    // Convierte la entidad Reporte a DTO para devolver una respuesta mas controlada.
    private ReporteDTO toDTO(Reporte r) {
        return ReporteDTO.builder()
            .id(r.getId())
            .perfilReportanteId(r.getPerfilReportante().getId())
            .perfilReportadoId(r.getPerfilReportado().getId())
            .razonReporte(r.getRazonReporte())
            .estadoReporte(r.getEstadoReporte())
            .fechaReporte(r.getFechaReporte())
            .activo(r.isActivo())
            .build();
    }
}
