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

// Los errores los atrapa GlobalExceptionHandler, ya no uso try-catch aca
@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionController {

    private static final Logger log = LoggerFactory.getLogger(NotificacionController.class);

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(@RequestParam Integer perfilDestinatarioId,
            @RequestParam String type, @RequestParam String message) {
        log.info("[v2] POST /api/v2/notificaciones - destinatario={}", perfilDestinatarioId);
        NotificacionDTO dto = notificacionService.crearNotificacion(perfilDestinatarioId, type, message);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/perfil/{perfilDestinatarioId}")
    public ResponseEntity<List<NotificacionDTO>> listar(@PathVariable Integer perfilDestinatarioId) {
        log.info("[v2] GET /api/v2/notificaciones/perfil/{}", perfilDestinatarioId);
        List<NotificacionDTO> lista = notificacionService.getPorPerfilDestinatario(perfilDestinatarioId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/no-leidas/{perfilDestinatarioId}")
    public ResponseEntity<List<NotificacionDTO>> obtenerNoLeidas(@PathVariable Integer perfilDestinatarioId) {
        log.info("[v2] GET /api/v2/notificaciones/no-leidas/{}", perfilDestinatarioId);
        List<NotificacionDTO> lista = notificacionService.getNoLeidas(perfilDestinatarioId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PatchMapping("/{id}/leido")
    public ResponseEntity<Void> marcarComoLeido(@PathVariable Integer id) {
        log.info("[v2] PATCH /api/v2/notificaciones/{}/leido", id);
        notificacionService.marcarComoLeida(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
