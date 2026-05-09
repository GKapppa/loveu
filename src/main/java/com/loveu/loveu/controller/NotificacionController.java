package com.loveu.loveu.controller;

import com.loveu.loveu.dto.NotificacionDTO;
import com.loveu.loveu.model.NotificacionType;
import com.loveu.loveu.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);
    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(
            @RequestParam Integer perfilDestinatarioId,
            @RequestParam NotificacionType type,
            @RequestParam String message) {
        log.info("POST /api/notificaciones perfilDestinatarioId={} type={}", perfilDestinatarioId, type);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.crearNotificacion(perfilDestinatarioId, type, message));
    }

    @GetMapping("/perfil/{perfilDestinatarioId}")
    public ResponseEntity<List<NotificacionDTO>> getPorPerfil(@PathVariable Integer perfilDestinatarioId) {
        log.info("GET /api/notificaciones/perfil/{}", perfilDestinatarioId);
        return ResponseEntity.ok(notificacionService.getNotificacionesPorPerfil(perfilDestinatarioId));
    }

    @GetMapping("/perfil/{perfilDestinatarioId}/no-leidas")
    public ResponseEntity<List<NotificacionDTO>> getNoLeidas(@PathVariable Integer perfilDestinatarioId) {
        log.info("GET /api/notificaciones/perfil/{}/no-leidas", perfilDestinatarioId);
        return ResponseEntity.ok(notificacionService.getNoLeidas(perfilDestinatarioId));
    }

    @GetMapping("/perfil/{perfilDestinatarioId}/conteo")
    public ResponseEntity<Long> contarNoLeidas(@PathVariable Integer perfilDestinatarioId) {
        log.info("GET /api/notificaciones/perfil/{}/conteo", perfilDestinatarioId);
        return ResponseEntity.ok(notificacionService.contarNoLeidas(perfilDestinatarioId));
    }

    @PatchMapping("/{id}/leida")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Integer id) {
        log.info("PATCH /api/notificaciones/{}/leida", id);
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("DELETE /api/notificaciones/{}", id);
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}