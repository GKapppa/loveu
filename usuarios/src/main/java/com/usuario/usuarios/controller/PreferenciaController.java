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
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuarios.dto.PreferenciaDTO;
import com.usuario.usuarios.service.PreferenciaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/preferencias")
public class PreferenciaController {

    private static final Logger log = LoggerFactory.getLogger(PreferenciaController.class);

    @Autowired
    private PreferenciaService preferenciaService;

    @PostMapping
    public ResponseEntity<?> crearPreferencia(@Valid @RequestBody PreferenciaDTO dto) {
        log.info("POST /api/v2/preferencias");
        try {
            PreferenciaDTO creado = preferenciaService.crearPreferencia(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            log.error("[v2] Error al crear preferencia: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("[v2] Error inesperado al crear preferencia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        log.info("GET /api/v2/preferencias");
        try {
            List<PreferenciaDTO> lista = preferenciaService.listarTodas();
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            log.error("[v2] Error al listar preferencias: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("[v2] Error inesperado al listar preferencias: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        log.info("GET /api/v2/preferencias/{}", id);
        try {
            PreferenciaDTO dto = preferenciaService.buscarPorId(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            log.error("[v2] Error al buscar preferencia por id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("[v2] Error inesperado al buscar preferencia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<?> buscarPorPerfil(@PathVariable Integer perfilId) {
        log.info("GET /api/v2/preferencias/perfil/{}", perfilId);
        try {
            PreferenciaDTO dto = preferenciaService.buscarPorPerfil(perfilId);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            log.error("[v2] Error al buscar preferencia por perfil: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("[v2] Error inesperado al buscar preferencia por perfil: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPreferencia(@PathVariable Integer id,
            @Valid @RequestBody PreferenciaDTO dto) {
        log.info("PUT /api/v2/preferencias/{}", id);
        try {
            PreferenciaDTO actualizado = preferenciaService.actualizarPreferencia(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            log.error("[v2] Error al actualizar preferencia: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("[v2] Error inesperado al actualizar preferencia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarPreferencia(@PathVariable Integer id) {
        log.info("DELETE /api/v2/preferencias/{}", id);
        try {
            preferenciaService.desactivarPreferencia(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("[v2] Error al desactivar preferencia: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("[v2] Error inesperado al desactivar preferencia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }
}
