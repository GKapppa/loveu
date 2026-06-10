package com.usuario.usuarios.controller;

import java.util.List;
import java.util.Map;

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
import com.usuario.usuarios.exceptions.ResourceNotFoundException;
import com.usuario.usuarios.service.PerfilService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/perfiles")
public class PerfilController {
    private static final Logger log = LoggerFactory.getLogger(PerfilController.class);

    @Autowired
    private PerfilService perfilService;

    @PostMapping
    public ResponseEntity<?> crearPerfil(@RequestParam Integer usuarioId,
        @RequestParam Integer comunaId){
        log.info("Post /api/v2/perfiles");
        try{
            PerfilDTO dto = perfilService.crearPerfil(usuarioId, comunaId);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e){
            log.error("[v2] Error al crear perfil: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PerfilDTO>> getTodos(){
        log.info("GET /api/v2/perfiles");
        return ResponseEntity.ok(perfilService.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        log.info("GET /api/v2/perfiles/{}", id);
        try{
            return ResponseEntity.ok(perfilService.getPorUsuario(id));
        } catch (RuntimeException e){
            log.error("[v2] Error al buscar perfil por id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPerfil(@PathVariable Integer id, @Valid @RequestBody PerfilDTO dto){
        log.info("PUT /api/v2/perfiles/{}", id);
        try{
            return ResponseEntity.ok(perfilService.actualizarPerfil(id, dto));
        } catch (ResourceNotFoundException e){
            log.error("[v2] Perfil a modificar no encontrado por id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e){
            log.error("[v2] Error al intentar actualizar un perfil con id: {} ", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarPerfil(@PathVariable Integer id){
        log.info("DELETE /api/v2/perfiles/{}", id);
        try{
            perfilService.desactivarPerfil(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e){
            log.error("[v2] Perfil a desactivar no encontrado por id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e){
            log.error("[v2] Error al intentar desactivar un perfil con id: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
