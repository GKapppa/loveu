package com.interaccion.interaccion.controller;

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

import com.interaccion.interaccion.dto.MensajeDTO;
import com.interaccion.interaccion.service.MensajeService;

@RestController
@RequestMapping("/api/v2/mensajes")
public class MensajeController {

    private static final Logger log = LoggerFactory.getLogger(MensajeController.class);

    @Autowired
    private MensajeService mensajeService;

    @PostMapping
    public ResponseEntity<?> enviarMensaje(@RequestParam Integer matchId,@RequestParam Integer perfilEmisorId,@RequestParam Integer perfilReceptorId,@RequestParam String contenido) {
        log.info("[v2] POST /api/v2/mensajes - matchId={}", matchId);
        try {
            MensajeDTO dto = mensajeService.enviarMensaje(matchId, perfilEmisorId, perfilReceptorId, contenido);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            log.error("[v2] Error al enviar mensaje: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getPorMatch(@PathVariable Integer matchId) {
        log.info("[v2] GET /api/v2/mensajes/match/{}", matchId);
        try {
            return ResponseEntity.ok(mensajeService.getPorMatch(matchId));
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener mensajes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/no-leidos/{perfilReceptorId}")
    public ResponseEntity<?> getNoLeidos(@PathVariable Integer perfilReceptorId) {
        log.info("[v2] GET /api/v2/mensajes/no-leidos/{}", perfilReceptorId);
        try {
            return ResponseEntity.ok(mensajeService.getNoLeidos(perfilReceptorId));
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener no leidos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/leido")
    public ResponseEntity<?> marcarComoLeido(@PathVariable Integer id) {
        log.info("[v2] PATCH /api/v2/mensajes/{}/leido", id);
        try {
            mensajeService.marcarComoLeido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("[v2] Error al marcar como leido: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
