package com.interaccion.interaccion.controller;

import java.util.List;
import java.util.Map;

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

@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionController {

    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestParam Integer perfilDestinatarioId, @RequestParam String type, @RequestParam String message) {
        log.info("[v2] POST /api/v2/notificaciones - destinatario={}", perfilDestinatarioId);
        try {
            NotificacionDTO dto = notificacionService.crearNotificacion(perfilDestinatarioId, type, message);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("[v2] Error al crear notificacion: {}", e.getMessage());
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/perfil/{perfilDestinatarioId}")
    public ResponseEntity<?> listar(@PathVariable Integer perfilDestinatarioId) {
        log.info("[v2] GET /api/v2/notificaciones/perfil/{}", perfilDestinatarioId);
        try {
            List<NotificacionDTO> lista = notificacionService.getPorPerfilDestinatario(perfilDestinatarioId);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener notificaciones: {}", e.getMessage());
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/no-leidas/{perfilDestinatarioId}")
    public ResponseEntity<?> obtenerNoLeidas(@PathVariable Integer perfilDestinatarioId) {
        log.info("[v2] GET /api/v2/notificaciones/no-leidas/{}", perfilDestinatarioId);
        try {
            List<NotificacionDTO> lista = notificacionService.getNoLeidas(perfilDestinatarioId);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener no leidas: {}", e.getMessage());
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/leido")
    public ResponseEntity<?> marcarComoLeido(@PathVariable Integer id) {
        log.info("[v2] PATCH /api/v2/notificaciones/{}/leido", id);
        try {
            notificacionService.marcarComoLeida(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("[v2] Error al marcar como leido: {}", e.getMessage());
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}