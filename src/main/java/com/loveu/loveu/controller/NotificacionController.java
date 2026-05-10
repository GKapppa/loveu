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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.NotificacionDTO;
import com.loveu.loveu.model.NotificacionType;
import com.loveu.loveu.service.NotificacionService;

// Controlador REST para crear, consultar y marcar notificaciones.
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    // Logger para ver en consola que endpoint se llamo y con que datos principales.
    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);
    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(
            // @RequestParam obtiene valores simples enviados como parametros.
            @RequestParam Integer perfilDestinatarioId,
            @RequestParam NotificacionType type,
            @RequestParam String message) {
        log.info("POST /api/notificaciones perfilDestinatarioId={} type={}", perfilDestinatarioId, type);
        // CREATED devuelve codigo HTTP 201 para indicar creacion exitosa.
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
    public ResponseEntity<Integer> contarNoLeidas(@PathVariable Integer perfilDestinatarioId) {
        log.info("GET /api/notificaciones/perfil/{}/conteo", perfilDestinatarioId);
        return ResponseEntity.ok(notificacionService.contarNoLeidas(perfilDestinatarioId));
    }

    // PATCH cambia solo el estado de lectura de la notificacion.
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
