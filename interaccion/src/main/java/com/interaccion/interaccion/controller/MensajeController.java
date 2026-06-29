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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// GlobalExceptionHandler se encarga de todo, ya no ensucio el codigo con try-catch
@RestController
@RequestMapping("/api/v2/mensajes")
@Tag(name = "Mensajes", description = "Mensajes entre perfiles matched")
public class MensajeController {

    private static final Logger log = LoggerFactory.getLogger(MensajeController.class);

    @Autowired
    private MensajeService mensajeService;

    @Operation(summary = "Enviar mensaje", description = "Envia un mensaje de un perfil a otro dentro de un match")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mensaje enviado"),
        @ApiResponse(responseCode = "400", description = "Self-mensaje no permitido")
    })
    @PostMapping
    public ResponseEntity<MensajeDTO> enviarMensaje(
            @Parameter(description = "ID del match") @RequestParam Integer matchId,
            @Parameter(description = "ID del perfil emisor") @RequestParam Integer perfilEmisorId,
            @Parameter(description = "ID del perfil receptor") @RequestParam Integer perfilReceptorId,
            @Parameter(description = "Contenido del mensaje") @RequestParam String contenido) {
        log.info("[v2] POST /api/v2/mensajes - matchId={}", matchId);
        MensajeDTO dto = mensajeService.enviarMensaje(matchId, perfilEmisorId, perfilReceptorId, contenido);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Mensajes por match", description = "Obtiene todos los mensajes de un match ordenados por fecha")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de mensajes")
    })
    @GetMapping("/match/{matchId}")
    public ResponseEntity<java.util.List<MensajeDTO>> getPorMatch(
            @Parameter(description = "ID del match") @PathVariable Integer matchId) {
        log.info("[v2] GET /api/v2/mensajes/match/{}", matchId);
        return ResponseEntity.ok(mensajeService.getPorMatch(matchId));
    }

    @Operation(summary = "Mensajes no leidos", description = "Obtiene los mensajes no leidos de un perfil receptor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de mensajes no leidos")
    })
    @GetMapping("/no-leidos/{perfilReceptorId}")
    public ResponseEntity<java.util.List<MensajeDTO>> getNoLeidos(
            @Parameter(description = "ID del perfil receptor") @PathVariable Integer perfilReceptorId) {
        log.info("[v2] GET /api/v2/mensajes/no-leidos/{}", perfilReceptorId);
        return ResponseEntity.ok(mensajeService.getNoLeidos(perfilReceptorId));
    }

    @Operation(summary = "Marcar mensaje como leido", description = "Marca un mensaje especifico como leido")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Mensaje marcado como leido"),
        @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    @PatchMapping("/{id}/leido")
    public ResponseEntity<Void> marcarComoLeido(
            @Parameter(description = "ID del mensaje") @PathVariable Integer id) {
        log.info("[v2] PATCH /api/v2/mensajes/{}/leido", id);
        mensajeService.marcarComoLeido(id);
        return ResponseEntity.noContent().build();
    }
}
