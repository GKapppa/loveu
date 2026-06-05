package com.loveu.loveu.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.NotificacionDTO;
import com.loveu.loveu.service.NotificacionService;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {
    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(@RequestBody NotificacionDTO dto) {
        log.info("POST /api/v1/notificaciones");

        if (dto == null) {
            throw new RuntimeException("Los datos de la notificacion son obligatorios");
        }

        log.info("Notificacion perfilDestinatarioId={} type={}", dto.getPerfilDestinatarioId(), dto.getType());

        if (dto.getPerfilDestinatarioId() == null) {
            throw new RuntimeException("El perfilDestinatarioId es obligatorio");
        }

        if (dto.getType() == null) {
            throw new RuntimeException("El tipo de notificacion es obligatorio");
        }

        if (dto.getMessage() == null || dto.getMessage().isBlank()) {
            throw new RuntimeException("El mensaje de la notificacion es obligatorio");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crearNotificacion(dto));
    }

    @GetMapping("/perfil/{perfilDestinatarioId}")
    public ResponseEntity<List<NotificacionDTO>> getPorPerfil(@PathVariable Integer perfilDestinatarioId) {
        log.info("GET /api/v1/notificaciones/perfil/{}", perfilDestinatarioId);
        return ResponseEntity.ok(notificacionService.getNotificacionesPorPerfil(perfilDestinatarioId));
    }

    @GetMapping("/perfil/{perfilDestinatarioId}/no-leidas")
    public ResponseEntity<List<NotificacionDTO>> getNoLeidas(@PathVariable Integer perfilDestinatarioId) {
        log.info("GET /api/v1/notificaciones/perfil/{}/no-leidas", perfilDestinatarioId);
        return ResponseEntity.ok(notificacionService.getNoLeidas(perfilDestinatarioId));
    }

    @GetMapping("/perfil/{perfilDestinatarioId}/conteo")
    public ResponseEntity<Integer> contarNoLeidas(@PathVariable Integer perfilDestinatarioId) {
        log.info("GET /api/v1/notificaciones/perfil/{}/conteo", perfilDestinatarioId);
        return ResponseEntity.ok(notificacionService.contarNoLeidas(perfilDestinatarioId));
    }

    @PatchMapping("/{id}/leida")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Integer id) {
        log.info("PATCH /api/v1/notificaciones/{}/leida", id);
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("DELETE /api/v1/notificaciones/{}", id);
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
