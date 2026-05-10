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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.PerfilDTO;
import com.loveu.loveu.service.PerfilService;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {
    private static final Logger log = LoggerFactory.getLogger(PerfilController.class);

    @Autowired
    private PerfilService perfilService;

    // El perfil es lo que realmente se muestra en la app.
    @PostMapping
    public ResponseEntity<PerfilDTO> crearPerfil(@RequestBody PerfilDTO dto){
        log.info("POST /api/perfiles");
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.crearPerfil(dto));
    }

    @GetMapping
    public ResponseEntity<List<PerfilDTO>> getTodos(){
        log.info("GET /api/perfiles");
        return ResponseEntity.ok(perfilService.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPorId(@PathVariable Integer id){
        log.info("GET /api/perfiles/{}", id);
        return ResponseEntity.ok(perfilService.getPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<PerfilDTO> getPorUsuario(@PathVariable Integer usuarioId){
        log.info("GET /api/perfiles/usuario/{}", usuarioId);
        return ResponseEntity.ok(perfilService.getPorUsuario(usuarioId));
    }

    @GetMapping("/comuna/{comunaId}")
    public ResponseEntity<List<PerfilDTO>> getPorComuna(@PathVariable Integer comunaId){
        log.info("GET /api/perfiles/comuna/{}", comunaId);
        return ResponseEntity.ok(perfilService.getPorComuna(comunaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(@PathVariable Integer id, @RequestBody PerfilDTO dto){
        log.info("PUT /api/perfiles/{}", id);
        return ResponseEntity.ok(perfilService.actualizarPerfil(id, dto));
    }

    // Baja logica para no perder historial del perfil.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarPerfil(@PathVariable Integer id){
        log.info("DELETE /api/perfiles/{}", id);
        perfilService.desactivarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}
