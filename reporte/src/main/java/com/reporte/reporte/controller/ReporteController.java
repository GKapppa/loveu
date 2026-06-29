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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// @RestControllerAdvice atrapa las excepciones, codigo mas limpio sin try-catch
@RestController
@RequestMapping("/api/v2/reportes")
@Tag(name = "Reportes", description = "Reportes de perfiles por comportamiento inadecuado")
public class ReporteController {

    private static final Logger log = LoggerFactory.getLogger(ReporteController.class);

    @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Crear reporte", description = "Registra un reporte de un perfil hacia otro")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reporte creado"),
        @ApiResponse(responseCode = "400", description = "Self-reporte o reporte duplicado")
    })
    @PostMapping
    public ResponseEntity<ReporteDTO> crearReporte(
            @Parameter(description = "ID del perfil que reporta") @RequestParam Integer perfilReportanteId,
            @Parameter(description = "ID del perfil reportado") @RequestParam Integer perfilReportadoId,
            @Parameter(description = "Razon del reporte") @RequestParam String razonReporte) {
        log.info("[v2] POST /api/v2/reportes - reportante={} reportado={}", perfilReportanteId, perfilReportadoId);
        ReporteDTO dto = reporteService.crearReporte(perfilReportanteId, perfilReportadoId, razonReporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Listar reportes", description = "Obtiene todos los reportes del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reportes")
    })
    @GetMapping
    public ResponseEntity<java.util.List<ReporteDTO>> getTodos() {
        log.info("[v2] GET /api/v2/reportes");
        return ResponseEntity.ok(reporteService.getTodos());
    }

    @Operation(summary = "Reportes por estado", description = "Filtra reportes segun su estado (PENDIENTE, REVISADO, RESUELTO)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reportes filtrados")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<java.util.List<ReporteDTO>> getPorEstado(
            @Parameter(description = "Estado del reporte") @PathVariable EstadoReporte estado) {
        log.info("[v2] GET /api/v2/reportes/estado/{}", estado);
        return ResponseEntity.ok(reporteService.getPorEstado(estado));
    }

    @Operation(summary = "Reportes por perfil reportado", description = "Obtiene todos los reportes hechos hacia un perfil especifico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reportes")
    })
    @GetMapping("/reportado/{perfilReportadoId}")
    public ResponseEntity<java.util.List<ReporteDTO>> getPorPerfilReportado(
            @Parameter(description = "ID del perfil reportado") @PathVariable Integer perfilReportadoId) {
        log.info("[v2] GET /api/v2/reportes/reportado/{}", perfilReportadoId);
        return ResponseEntity.ok(reporteService.getPorPerfilReportado(perfilReportadoId));
    }

    @Operation(summary = "Actualizar estado", description = "Cambia el estado de un reporte (PENDIENTE -> REVISADO -> RESUELTO)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReporteDTO> actualizarEstado(
            @Parameter(description = "ID del reporte") @PathVariable Integer id,
            @Parameter(description = "Nuevo estado") @RequestParam EstadoReporte nuevoEstado) {
        log.info("[v2] PATCH /api/v2/reportes/{}/estado -> {}", id, nuevoEstado);
        return ResponseEntity.ok(reporteService.actualizarEstado(id, nuevoEstado));
    }

    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte (soft delete)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Reporte eliminado"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(
            @Parameter(description = "ID del reporte") @PathVariable Integer id) {
        log.info("[v2] DELETE /api/v2/reportes/{}", id);
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
