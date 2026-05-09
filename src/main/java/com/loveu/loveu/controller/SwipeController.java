package com.loveu.loveu.controller;

import com.loveu.loveu.dto.SwipeDTO;
import com.loveu.loveu.model.Swipe;
import com.loveu.loveu.service.SwipeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/swipes")
@RequiredArgsConstructor
public class SwipeController {
    private static final Logger log = LoggerFactory.getLogger(SwipeController.class);
    private final SwipeService swipeService;

    @PostMapping
    public ResponseEntity<SwipeDTO> registrarSwipe(@RequestBody SwipeDTO dto) {
        log.info("POST /api/swipes");
        return ResponseEntity.status(HttpStatus.CREATED).body(swipeService.registrarSwipe(dto));
    }

    @GetMapping
    public ResponseEntity<List<Swipe>> getTodos() {
        log.info("GET /api/swipes");
        return ResponseEntity.ok(swipeService.obtenerTodos());
    }

    @GetMapping("/origen/{perfilOrigenId}")
    public ResponseEntity<List<Swipe>> getPorPerfilOrigen(@PathVariable Integer perfilOrigenId) {
        log.info("GET /api/swipes/origen/{}", perfilOrigenId);
        return ResponseEntity.ok(swipeService.obtenerPorPerfilOrigen(perfilOrigenId));
    }

    @GetMapping("/destino/{perfilDestinoId}")
    public ResponseEntity<List<Swipe>> getPorPerfilDestino(@PathVariable Integer perfilDestinoId) {
        log.info("GET /api/swipes/destino/{}", perfilDestinoId);
        return ResponseEntity.ok(swipeService.obtenerPorPerfilDestino(perfilDestinoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSwipe(@PathVariable Integer id) {
        log.info("DELETE /api/swipes/{}", id);
        swipeService.eliminarSwipe(id);
        return ResponseEntity.noContent().build();
    }
}