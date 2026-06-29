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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// @RestControllerAdvice atrapa todo, asi no ensucio el codigo con try-catch
@RestController
@RequestMapping("/api/v2/swipes")
@Tag(name = "Swipes", description = "Swipe de perfiles (LIKE o NOPE)")
public class SwipeController {

    private static final Logger log = LoggerFactory.getLogger(SwipeController.class);

    @Autowired
    private SwipeService swipeService;

    @Operation(summary = "Crear swipe", description = "Registra un swipe (LIKE o NOPE) de un perfil hacia otro")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Swipe creado"),
        @ApiResponse(responseCode = "400", description = "Swipe duplicado o self-swipe")
    })
    @PostMapping
    public ResponseEntity<SwipeDTO> crearSwipe(
            @Parameter(description = "ID del perfil que hace el swipe") @RequestParam Integer perfilOrigenId,
            @Parameter(description = "ID del perfil destino") @RequestParam Integer perfilDestinoId,
            @Parameter(description = "Decision: LIKE o NOPE") @RequestParam String decision) {
        log.info("[v2] POST /api/v2/swipes - origen={} destino={} decision={}", perfilOrigenId, perfilDestinoId, decision);
        SwipeDTO dto = swipeService.crearSwipe(perfilOrigenId, perfilDestinoId, decision);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Listar todos los swipes", description = "Obtiene todos los swipes del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de swipes")
    })
    @GetMapping
    public ResponseEntity<java.util.List<SwipeDTO>> getTodos() {
        log.info("[v2] GET /api/v2/swipes");
        return ResponseEntity.ok(swipeService.getTodos());
    }

    @Operation(summary = "Swipes por perfil origen", description = "Obtiene todos los swipes hechos por un perfil especifico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de swipes")
    })
    @GetMapping("/origen/{perfilOrigenId}")
    public ResponseEntity<java.util.List<SwipeDTO>> getPorOrigen(
            @Parameter(description = "ID del perfil origen") @PathVariable Integer perfilOrigenId) {
        log.info("[v2] GET /api/v2/swipes/origen/{}", perfilOrigenId);
        return ResponseEntity.ok(swipeService.getPorOrigen(perfilOrigenId));
    }

    @Operation(summary = "Swipes por perfil destino", description = "Obtiene todos los swipes recibidos por un perfil especifico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de swipes")
    })
    @GetMapping("/destino/{perfilDestinoId}")
    public ResponseEntity<java.util.List<SwipeDTO>> getPorDestino(
            @Parameter(description = "ID del perfil destino") @PathVariable Integer perfilDestinoId) {
        log.info("[v2] GET /api/v2/swipes/destino/{}", perfilDestinoId);
        return ResponseEntity.ok(swipeService.getPorDestino(perfilDestinoId));
    }
}
