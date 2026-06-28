package com.reporte.reporte.controller;

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

import com.reporte.reporte.dto.ReporteDTO;
import com.reporte.reporte.model.EstadoReporte;
import com.reporte.reporte.service.ReporteService;

// @RestControllerAdvice atrapa las excepciones, codigo mas limpio sin try-catch
@RestController
@RequestMapping("/api/v2/reportes")
public class ReporteController {

    private static final Logger log = LoggerFactory.getLogger(ReporteController.class);

    @Autowired
    private ReporteService reporteService;

    @PostMapping
    public ResponseEntity<ReporteDTO> crearReporte(
            @RequestParam Integer perfilReportanteId,
            @RequestParam Integer perfilReportadoId,
            @RequestParam String razonReporte) {
        log.info("[v2] POST /api/v2/reportes - reportante={} reportado={}", perfilReportanteId, perfilReportadoId);
        ReporteDTO dto = reporteService.crearReporte(perfilReportanteId, perfilReportadoId, razonReporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<java.util.List<ReporteDTO>> getTodos() {
        log.info("[v2] GET /api/v2/reportes");
        return ResponseEntity.ok(reporteService.getTodos());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<java.util.List<ReporteDTO>> getPorEstado(@PathVariable EstadoReporte estado) {
        log.info("[v2] GET /api/v2/reportes/estado/{}", estado);
        return ResponseEntity.ok(reporteService.getPorEstado(estado));
    }

    @GetMapping("/reportado/{perfilReportadoId}")
    public ResponseEntity<java.util.List<ReporteDTO>> getPorPerfilReportado(
            @PathVariable Integer perfilReportadoId) {
        log.info("[v2] GET /api/v2/reportes/reportado/{}", perfilReportadoId);
        return ResponseEntity.ok(reporteService.getPorPerfilReportado(perfilReportadoId));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReporteDTO> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoReporte nuevoEstado) {
        log.info("[v2] PATCH /api/v2/reportes/{}/estado -> {}", id, nuevoEstado);
        return ResponseEntity.ok(reporteService.actualizarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Integer id) {
        log.info("[v2] DELETE /api/v2/reportes/{}", id);
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
