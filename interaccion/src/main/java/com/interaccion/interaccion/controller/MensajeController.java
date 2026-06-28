package com.interaccion.interaccion.controller;

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

// GlobalExceptionHandler se encarga de todo, ya no ensucio el codigo con try-catch
@RestController
@RequestMapping("/api/v2/mensajes")
public class MensajeController {

    private static final Logger log = LoggerFactory.getLogger(MensajeController.class);

    @Autowired
    private MensajeService mensajeService;

    @PostMapping
    public ResponseEntity<MensajeDTO> enviarMensaje(@RequestParam Integer matchId,
            @RequestParam Integer perfilEmisorId,
            @RequestParam Integer perfilReceptorId,
            @RequestParam String contenido) {
        log.info("[v2] POST /api/v2/mensajes - matchId={}", matchId);
        MensajeDTO dto = mensajeService.enviarMensaje(matchId, perfilEmisorId, perfilReceptorId, contenido);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<java.util.List<MensajeDTO>> getPorMatch(@PathVariable Integer matchId) {
        log.info("[v2] GET /api/v2/mensajes/match/{}", matchId);
        return ResponseEntity.ok(mensajeService.getPorMatch(matchId));
    }

    @GetMapping("/no-leidos/{perfilReceptorId}")
    public ResponseEntity<java.util.List<MensajeDTO>> getNoLeidos(@PathVariable Integer perfilReceptorId) {
        log.info("[v2] GET /api/v2/mensajes/no-leidos/{}", perfilReceptorId);
        return ResponseEntity.ok(mensajeService.getNoLeidos(perfilReceptorId));
    }

    @PatchMapping("/{id}/leido")
    public ResponseEntity<Void> marcarComoLeido(@PathVariable Integer id) {
        log.info("[v2] PATCH /api/v2/mensajes/{}/leido", id);
        mensajeService.marcarComoLeido(id);
        return ResponseEntity.noContent().build();
    }
}
