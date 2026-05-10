package com.loveu.loveu.controller;

import com.loveu.loveu.dto.ReporteDTO;
import com.loveu.loveu.model.EstadoReporte;
import com.loveu.loveu.service.ReporteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST encargado de exponer los endpoints de reportes.
@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    // log.info permite dejar una traza simple de las peticiones ejecutadas.
    private static final Logger log = LoggerFactory.getLogger(ReporteController.class);
    @Autowired
    private ReporteService reporteService;

    @PostMapping
    public ResponseEntity<ReporteDTO> crear(
            // @RequestParam recibe datos simples desde parametros de la peticion.
            @RequestParam Integer perfilReportanteId,
            @RequestParam Integer perfilReportadoId,
            @RequestParam String razonReporte) {
        log.info("POST /api/reportes reportante={} reportado={}", perfilReportanteId, perfilReportadoId);
        // status(CREATED) responde HTTP 201 porque se crea un reporte nuevo.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reporteService.crearReporte(perfilReportanteId, perfilReportadoId, razonReporte));
    }

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> getTodos() {
        log.info("GET /api/reportes");
        return ResponseEntity.ok(reporteService.getTodos());
    }

    @GetMapping("/estado/{estadoReporte}")
    public ResponseEntity<List<ReporteDTO>> getPorEstado(@PathVariable EstadoReporte estadoReporte) {
        log.info("GET /api/reportes/estado/{}", estadoReporte);
        return ResponseEntity.ok(reporteService.getPorEstado(estadoReporte));
    }

    @GetMapping("/reportado/{perfilReportadoId}")
    public ResponseEntity<List<ReporteDTO>> getPorPerfilReportado(@PathVariable Integer perfilReportadoId) {
        log.info("GET /api/reportes/reportado/{}", perfilReportadoId);
        return ResponseEntity.ok(reporteService.getPorPerfilReportado(perfilReportadoId));
    }

    // PATCH actualiza solo el estado del reporte.
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReporteDTO> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoReporte nuevoEstado) {
        log.info("PATCH /api/reportes/{}/estado", id);
        return ResponseEntity.ok(reporteService.actualizarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("DELETE /api/reportes/{}", id);
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
