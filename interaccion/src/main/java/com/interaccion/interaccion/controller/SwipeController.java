package com.interaccion.interaccion.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interaccion.interaccion.dto.SwipeDTO;
import com.interaccion.interaccion.service.SwipeService;

@RestController
@RequestMapping("/api/v2/swipes")
public class SwipeController {

    private static final Logger log = LoggerFactory.getLogger(SwipeController.class);

    @Autowired
    private SwipeService swipeService;

    @PostMapping
    public ResponseEntity<?> crearSwipe(
            @RequestParam Integer perfilOrigenId,
            @RequestParam Integer perfilDestinoId,
            @RequestParam String decision) {
        log.info("[v2] POST /api/v2/swipes - origen={} destino={} decision={}", perfilOrigenId, perfilDestinoId, decision);
        try {
            SwipeDTO dto = swipeService.crearSwipe(perfilOrigenId, perfilDestinoId, decision);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            log.error("[v2] Error al crear swipe: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getTodos() {
        log.info("[v2] GET /api/v2/swipes");
        try {
            return ResponseEntity.ok(swipeService.getTodos());
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener swipes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/origen/{perfilOrigenId}")
    public ResponseEntity<?> getPorOrigen(@PathVariable Integer perfilOrigenId) {
        log.info("[v2] GET /api/v2/swipes/origen/{}", perfilOrigenId);
        try {
            return ResponseEntity.ok(swipeService.getPorOrigen(perfilOrigenId));
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener swipes por origen: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/destino/{perfilDestinoId}")
    public ResponseEntity<?> getPorDestino(@PathVariable Integer perfilDestinoId) {
        log.info("[v2] GET /api/v2/swipes/destino/{}", perfilDestinoId);
        try {
            return ResponseEntity.ok(swipeService.getPorDestino(perfilDestinoId));
        } catch (RuntimeException e) {
            log.error("[v2] Error al obtener swipes por destino: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
