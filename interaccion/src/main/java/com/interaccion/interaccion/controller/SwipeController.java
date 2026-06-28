package com.interaccion.interaccion.controller;

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

// @RestControllerAdvice atrapa todo, asi no ensucio el codigo con try-catch
@RestController
@RequestMapping("/api/v2/swipes")
public class SwipeController {

    private static final Logger log = LoggerFactory.getLogger(SwipeController.class);

    @Autowired
    private SwipeService swipeService;

    @PostMapping
    public ResponseEntity<SwipeDTO> crearSwipe(
            @RequestParam Integer perfilOrigenId,
            @RequestParam Integer perfilDestinoId,
            @RequestParam String decision) {
        log.info("[v2] POST /api/v2/swipes - origen={} destino={} decision={}", perfilOrigenId, perfilDestinoId,
                decision);
        SwipeDTO dto = swipeService.crearSwipe(perfilOrigenId, perfilDestinoId, decision);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<java.util.List<SwipeDTO>> getTodos() {
        log.info("[v2] GET /api/v2/swipes");
        return ResponseEntity.ok(swipeService.getTodos());
    }

    @GetMapping("/origen/{perfilOrigenId}")
    public ResponseEntity<java.util.List<SwipeDTO>> getPorOrigen(@PathVariable Integer perfilOrigenId) {
        log.info("[v2] GET /api/v2/swipes/origen/{}", perfilOrigenId);
        return ResponseEntity.ok(swipeService.getPorOrigen(perfilOrigenId));
    }

    @GetMapping("/destino/{perfilDestinoId}")
    public ResponseEntity<java.util.List<SwipeDTO>> getPorDestino(@PathVariable Integer perfilDestinoId) {
        log.info("[v2] GET /api/v2/swipes/destino/{}", perfilDestinoId);
        return ResponseEntity.ok(swipeService.getPorDestino(perfilDestinoId));
    }
}
