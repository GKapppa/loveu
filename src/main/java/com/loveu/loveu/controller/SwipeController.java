package com.loveu.loveu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.SwipeDTO;
import com.loveu.loveu.service.SwipeService;

@RestController
@RequestMapping("/api/swipes")
public class SwipeController {
    private static final Logger log = LoggerFactory.getLogger(SwipeController.class);
    @Autowired
    private SwipeService swipeService;

    @PostMapping
    public ResponseEntity<SwipeDTO> registrarSwipe(@RequestBody SwipeDTO dto) {
        log.info("POST /api/swipes");

        if (dto == null) {
            throw new RuntimeException("Los datos del swipe son obligatorios");
        }

        if (dto.getPerfilOrigenId() == null || dto.getPerfilDestinoId() == null) {
            throw new RuntimeException("Los perfiles origen y destino son obligatorios");
        }

        if (dto.getDecision() == null) {
            throw new RuntimeException("La decision del swipe es obligatoria");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(swipeService.registrarSwipe(dto));
    }

    @GetMapping
    public ResponseEntity<List<SwipeDTO>> getTodos() {
        log.info("GET /api/swipes");
        return ResponseEntity.ok(swipeService.obtenerTodos());
    }

    @GetMapping("/origen/{perfilOrigenId}")
    public ResponseEntity<List<SwipeDTO>> getPorPerfilOrigen(@PathVariable Integer perfilOrigenId) {
        log.info("GET /api/swipes/origen/{}", perfilOrigenId);
        return ResponseEntity.ok(swipeService.obtenerPorPerfilOrigen(perfilOrigenId));
    }

    @GetMapping("/destino/{perfilDestinoId}")
    public ResponseEntity<List<SwipeDTO>> getPorPerfilDestino(@PathVariable Integer perfilDestinoId) {
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
