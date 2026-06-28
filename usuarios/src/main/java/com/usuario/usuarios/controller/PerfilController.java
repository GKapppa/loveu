package com.usuario.usuarios.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuarios.dto.PerfilDTO;
import com.usuario.usuarios.service.PerfilService;

import jakarta.validation.Valid;

// Las excepciones las atrapa GlobalExceptionHandler, ya no necesito try-catch aca
@RestController
@RequestMapping("/api/v2/perfiles")
public class PerfilController {
    private static final Logger log = LoggerFactory.getLogger(PerfilController.class);

    @Autowired
    private PerfilService perfilService;

    @PostMapping
    public ResponseEntity<PerfilDTO> crearPerfil(@RequestParam Integer usuarioId,
            @RequestParam Integer comunaId) {
        log.info("Post /api/v2/perfiles");
        PerfilDTO dto = perfilService.crearPerfil(usuarioId, comunaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<PerfilDTO>> getTodos() {
        log.info("GET /api/v2/perfiles");
        return ResponseEntity.ok(perfilService.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPorId(@PathVariable Integer id) {
        log.info("GET /api/v2/perfiles/{}", id);
        return ResponseEntity.ok(perfilService.getPorUsuario(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(@PathVariable Integer id,
            @Valid @RequestBody PerfilDTO dto) {
        log.info("PUT /api/v2/perfiles/{}", id);
        return ResponseEntity.ok(perfilService.actualizarPerfil(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarPerfil(@PathVariable Integer id) {
        log.info("DELETE /api/v2/perfiles/{}", id);
        perfilService.desactivarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}
