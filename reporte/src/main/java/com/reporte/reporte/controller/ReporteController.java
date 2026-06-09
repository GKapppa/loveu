package com.reporte.reporte.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reporte.reporte.DTO.ReporteDTO;
import com.reporte.reporte.model.EstadoReporte;
import com.reporte.reporte.service.ReporteService;

@RestController
@RequestMapping("/api/v2/reportes")
public class ReporteController {

    private static final Logger log = LoggerFactory.getLogger(ReporteController.class);

    @Autowired
    private ReporteService reporteService;

    @PostMapping
    public ResponseEntity<?> crearReporte(
            @RequestParam Integer perfilReportanteId,
            @RequestParam Integer perfilReportadoId,
            @RequestParam String razonReporte) {
        log.info("[v2] POST /api/v2/reportes - reportante={} reportado={}", perfilReportanteId, perfilReportadoId);
        try {
            ReporteDTO dto = reporteService.crearReporte(perfilReportanteId, perfilReportadoId, razonReporte);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            log.error("[v2] Error al crear reporte: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getTodos() {
        log.info("[v2] GET /api/v2/reportes");
        try {
            return ResponseEntity.ok(reporteService.getTodos());
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener reportes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getPorEstado(@PathVariable EstadoReporte estado) {
        log.info("[v2] GET /api/v2/reportes/estado/{}", estado);
        try {
            return ResponseEntity.ok(reporteService.getPorEstado(estado));
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener reportes por estado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/reportado/{perfilReportadoId}")
    public ResponseEntity<?> getPorPerfilReportado(@PathVariable Integer perfilReportadoId) {
        log.info("[v2] GET /api/v2/reportes/reportado/{}", perfilReportadoId);
        try {
            return ResponseEntity.ok(reporteService.getPorPerfilReportado(perfilReportadoId));
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener reportes del perfil: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoReporte nuevoEstado) {
        log.info("[v2] PATCH /api/v2/reportes/{}/estado -> {}", id, nuevoEstado);
        try {
            return ResponseEntity.ok(reporteService.actualizarEstado(id, nuevoEstado));
        } catch (RuntimeException e) {
            log.error("[v2] Error al actualizar estado: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReporte(@PathVariable Integer id) {
        log.info("[v2] DELETE /api/v2/reportes/{}", id);
        try {
            reporteService.eliminarReporte(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("[v2] Error al eliminar reporte: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
