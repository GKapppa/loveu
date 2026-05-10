package com.loveu.loveu.controller;

import com.loveu.loveu.dto.MensajeDTO;
import com.loveu.loveu.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para operaciones de mensajes dentro de un match.
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    // Logger para registrar en consola cada accion importante del controlador.
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private ChatService chatService;

    @PostMapping("/mensajes")
    // @RequestBody convierte el JSON recibido en un MensajeDTO.
    public ResponseEntity<MensajeDTO> enviarMensaje(@RequestBody MensajeDTO dto) {
        log.info("POST /api/chat/mensajes");
        // CREATED corresponde al codigo HTTP 201 cuando se crea un recurso.
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.enviarMensaje(dto));
    }

    @GetMapping("/match/{matchId}")
    // @PathVariable lee el id enviado dentro de la URL.
    public ResponseEntity<List<MensajeDTO>> getPorMatch(@PathVariable Integer matchId) {
        log.info("GET /api/chat/match/{}", matchId);
        return ResponseEntity.ok(chatService.getMensajesPorMatch(matchId));
    }

    @GetMapping("/no-leidos/{perfilReceptorId}")
    public ResponseEntity<List<MensajeDTO>> getNoLeidos(@PathVariable Integer perfilReceptorId) {
        log.info("GET /api/chat/no-leidos/{}", perfilReceptorId);
        return ResponseEntity.ok(chatService.getMensajesNoLeidos(perfilReceptorId));
    }

    // PATCH modifica solo el estado de lectura, no reemplaza el mensaje completo.
    @PatchMapping("/mensajes/{id}/leido")
    public ResponseEntity<Void> marcarComoLeido(@PathVariable Integer id) {
        log.info("PATCH /api/chat/mensajes/{}/leido", id);
        chatService.marcarComoLeido(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/mensajes/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Integer id) {
        log.info("DELETE /api/chat/mensajes/{}", id);
        chatService.eliminarMensaje(id);
        return ResponseEntity.noContent().build();
    }
}
