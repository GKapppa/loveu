package com.interaccion.interaccion.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interaccion.interaccion.dto.NotificacionDTO;
import com.interaccion.interaccion.service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// Los errores los atrapa GlobalExceptionHandler, ya no uso try-catch aca
@RestController
@RequestMapping("/api/v2/notificaciones")
@Tag(name = "Notificaciones", description = "Notificaciones de matches y mensajes")
public class NotificacionController {

    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);

    @Autowired
    private NotificacionService notificacionService;

    @Operation(summary = "Crear notificacion", description = "Crea una notificacion para un perfil destinatario")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Notificacion creada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(
            @Parameter(description = "ID del perfil destinatario") @RequestParam Integer perfilDestinatarioId,
            @Parameter(description = "Tipo de notificacion") @RequestParam String type,
            @Parameter(description = "Mensaje de la notificacion") @RequestParam String message) {
        log.info("[v2] POST /api/v2/notificaciones - destinatario={}", perfilDestinatarioId);
        NotificacionDTO dto = notificacionService.crearNotificacion(perfilDestinatarioId, type, message);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Notificaciones por perfil", description = "Obtiene todas las notificaciones de un perfil")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de notificaciones")
    })
    @GetMapping("/perfil/{perfilDestinatarioId}")
    public ResponseEntity<List<NotificacionDTO>> listar(
            @Parameter(description = "ID del perfil destinatario") @PathVariable Integer perfilDestinatarioId) {
        log.info("[v2] GET /api/v2/notificaciones/perfil/{}", perfilDestinatarioId);
        List<NotificacionDTO> lista = notificacionService.getPorPerfilDestinatario(perfilDestinatarioId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Notificaciones no leidas", description = "Obtiene solo las notificaciones no leidas de un perfil")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de notificaciones no leidas")
    })
    @GetMapping("/no-leidas/{perfilDestinatarioId}")
    public ResponseEntity<List<NotificacionDTO>> obtenerNoLeidas(
            @Parameter(description = "ID del perfil destinatario") @PathVariable Integer perfilDestinatarioId) {
        log.info("[v2] GET /api/v2/notificaciones/no-leidas/{}", perfilDestinatarioId);
        List<NotificacionDTO> lista = notificacionService.getNoLeidas(perfilDestinatarioId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Marcar como leida", description = "Marca una notificacion especifica como leida")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Notificacion marcada como leida"),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
    })
    @PatchMapping("/{id}/leido")
    public ResponseEntity<Void> marcarComoLeido(
            @Parameter(description = "ID de la notificacion") @PathVariable Integer id) {
        log.info("[v2] PATCH /api/v2/notificaciones/{}/leido", id);
        notificacionService.marcarComoLeida(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
