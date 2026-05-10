package com.loveu.loveu.controller;

import com.loveu.loveu.dto.ReporteDTO;
import com.loveu.loveu.model.EstadoReporte;
import com.loveu.loveu.service.ReporteService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    private static final Logger log = LoggerFactory.getLogger(ReporteController.class);

    @Autowired
    private ReporteService reporteService;

    // Se recibe JSON para no mandar datos largos en la URL.
    @PostMapping
    public ResponseEntity<ReporteDTO> crear(@RequestBody ReporteDTO dto) {
        log.info("POST /api/reportes reportante={} reportado={}", dto.getPerfilReportanteId(), dto.getPerfilReportadoId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reporteService.crearReporte(dto.getPerfilReportanteId(), dto.getPerfilReportadoId(), dto.getRazonReporte()));
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
