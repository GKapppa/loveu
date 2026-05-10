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

// @RestController indica que esta clase expone endpoints HTTP y responde en JSON.
@RestController
// Ruta base: todos los endpoints de esta clase empiezan con /api/swipes.
@RequestMapping("/api/swipes")
public class SwipeController {
    // Logger para dejar registro en consola de las peticiones recibidas.
    private static final Logger log = LoggerFactory.getLogger(SwipeController.class);
    @Autowired
    private SwipeService swipeService;

    // @PostMapping atiende peticiones POST; @RequestBody toma el JSON enviado y lo convierte en SwipeDTO.
    @PostMapping
    public ResponseEntity<SwipeDTO> registrarSwipe(@RequestBody SwipeDTO dto) {
        log.info("POST /api/swipes");
        // ResponseEntity permite controlar codigo HTTP y cuerpo de la respuesta.
        return ResponseEntity.status(HttpStatus.CREATED).body(swipeService.registrarSwipe(dto));
    }

    // @GetMapping atiende consultas GET para listar informacion.
    @GetMapping
    public ResponseEntity<List<SwipeDTO>> getTodos() {
        log.info("GET /api/swipes");
        return ResponseEntity.ok(swipeService.obtenerTodos());
    }

    @GetMapping("/origen/{perfilOrigenId}")
    // @PathVariable toma el valor que viene dentro de la URL.
    public ResponseEntity<List<SwipeDTO>> getPorPerfilOrigen(@PathVariable Integer perfilOrigenId) {
        log.info("GET /api/swipes/origen/{}", perfilOrigenId);
        return ResponseEntity.ok(swipeService.obtenerPorPerfilOrigen(perfilOrigenId));
    }

    @GetMapping("/destino/{perfilDestinoId}")
    public ResponseEntity<List<SwipeDTO>> getPorPerfilDestino(@PathVariable Integer perfilDestinoId) {
        log.info("GET /api/swipes/destino/{}", perfilDestinoId);
        return ResponseEntity.ok(swipeService.obtenerPorPerfilDestino(perfilDestinoId));
    }

    // @DeleteMapping atiende DELETE; noContent() responde 204 cuando no hay cuerpo que devolver.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSwipe(@PathVariable Integer id) {
        log.info("DELETE /api/swipes/{}", id);
        swipeService.eliminarSwipe(id);
        return ResponseEntity.noContent().build();
    }
}
