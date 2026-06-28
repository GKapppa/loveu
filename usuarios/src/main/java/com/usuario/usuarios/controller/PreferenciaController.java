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
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuarios.dto.PreferenciaDTO;
import com.usuario.usuarios.service.PreferenciaService;

import jakarta.validation.Valid;

// GlobalExceptionHandler se encarga de todo, ya no ocupo try-catch
@RestController
@RequestMapping("/api/v2/preferencias")
public class PreferenciaController {

    private static final Logger log = LoggerFactory.getLogger(PreferenciaController.class);

    @Autowired
    private PreferenciaService preferenciaService;

    @PostMapping
    public ResponseEntity<PreferenciaDTO> crearPreferencia(@Valid @RequestBody PreferenciaDTO dto) {
        log.info("POST /api/v2/preferencias");
        PreferenciaDTO creado = preferenciaService.crearPreferencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<PreferenciaDTO>> listarTodas() {
        log.info("GET /api/v2/preferencias");
        return ResponseEntity.ok(preferenciaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenciaDTO> buscarPorId(@PathVariable Integer id) {
        log.info("GET /api/v2/preferencias/{}", id);
        return ResponseEntity.ok(preferenciaService.buscarPorId(id));
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<PreferenciaDTO> buscarPorPerfil(@PathVariable Integer perfilId) {
        log.info("GET /api/v2/preferencias/perfil/{}", perfilId);
        return ResponseEntity.ok(preferenciaService.buscarPorPerfil(perfilId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreferenciaDTO> actualizarPreferencia(@PathVariable Integer id,
            @Valid @RequestBody PreferenciaDTO dto) {
        log.info("PUT /api/v2/preferencias/{}", id);
        return ResponseEntity.ok(preferenciaService.actualizarPreferencia(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarPreferencia(@PathVariable Integer id) {
        log.info("DELETE /api/v2/preferencias/{}", id);
        preferenciaService.desactivarPreferencia(id);
        return ResponseEntity.noContent().build();
    }
}
