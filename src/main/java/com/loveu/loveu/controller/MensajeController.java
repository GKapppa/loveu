package com.loveu.loveu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.MensajeDTO;
import com.loveu.loveu.service.ChatService;

@RestController
@RequestMapping("/api/v1/chat")
public class MensajeController {
    private static final Logger log = LoggerFactory.getLogger(MensajeController.class);
    @Autowired
    private ChatService chatService;

    @PostMapping("/mensajes")
    public ResponseEntity<MensajeDTO> enviarMensaje(@RequestBody MensajeDTO dto) {
        log.info("POST /api/v1/chat/mensajes");
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.enviarMensaje(dto));
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<MensajeDTO>> getPorMatch(@PathVariable Integer matchId) {
        log.info("GET /api/v1/chat/match/{}", matchId);
        return ResponseEntity.ok(chatService.getMensajesPorMatch(matchId));
    }

    @GetMapping("/no-leidos/{perfilReceptorId}")
    public ResponseEntity<List<MensajeDTO>> getNoLeidos(@PathVariable Integer perfilReceptorId) {
        log.info("GET /api/v1/chat/no-leidos/{}", perfilReceptorId);
        return ResponseEntity.ok(chatService.getMensajesNoLeidos(perfilReceptorId));
    }

    @PatchMapping("/mensajes/{id}/leido")
    public ResponseEntity<Void> marcarComoLeido(@PathVariable Integer id) {
        log.info("PATCH /api/v1/chat/mensajes/{}/leido", id);
        chatService.marcarComoLeido(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/mensajes/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Integer id) {
        log.info("DELETE /api/v1/chat/mensajes/{}", id);
        chatService.eliminarMensaje(id);
        return ResponseEntity.noContent().build();
    }
}
