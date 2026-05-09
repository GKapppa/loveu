package com.loveu.loveu.controller;

import com.loveu.loveu.dto.MensajeDTO;
import com.loveu.loveu.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatService chatService;

    @PostMapping("/mensajes")
    public ResponseEntity<MensajeDTO> enviarMensaje(@RequestBody MensajeDTO dto) {
        log.info("POST /api/chat/mensajes");
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.enviarMensaje(dto));
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<MensajeDTO>> getPorMatch(@PathVariable Integer matchId) {
        log.info("GET /api/chat/match/{}", matchId);
        return ResponseEntity.ok(chatService.getMensajesPorMatch(matchId));
    }

    @GetMapping("/no-leidos/{perfilReceptorId}")
    public ResponseEntity<List<MensajeDTO>> getNoLeidos(@PathVariable Integer perfilReceptorId) {
        log.info("GET /api/chat/no-leidos/{}", perfilReceptorId);
        return ResponseEntity.ok(chatService.getMensajesNoLeidos(perfilReceptorId));
    }

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
