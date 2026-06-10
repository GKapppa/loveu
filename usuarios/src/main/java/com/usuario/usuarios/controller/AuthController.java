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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.usuario.usuarios.dto.AuthDTO;
import com.usuario.usuarios.dto.AuthRequestDTO;
import com.usuario.usuarios.service.AuthService;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> crearAuth(@RequestParam Integer usuarioId, @Valid @RequestBody AuthRequestDTO authDTO) {
        log.info("[v2] POST /api/v2/auth para usuarioId={}", usuarioId);
        try {
            AuthDTO dto = authService.crearAuth(usuarioId, authDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            log.error("[v2] Error al crear auth: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO authDTO) {
        log.info("[v2] POST /api/v2/auth/login");
        try {
            return ResponseEntity.ok(authService.login(authDTO));
        } catch (RuntimeException e) {
            log.error("[v2] Error al iniciar sesion: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        log.info("[v2] GET /api/v2/auth");
        try {
            return ResponseEntity.ok(authService.listarTodos());
        } catch (RuntimeException e) {
            log.error("[v2] Error al listar auths: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        log.info("[v2] GET /api/v2/auth/email/{}", email);
        try {
            return ResponseEntity.ok(authService.buscarPorEmail(email));
        } catch (RuntimeException e) {
            log.error("[v2] Error al buscar email: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desactivarAuth(@PathVariable Integer id) {
        log.info("[v2] DELETE /api/v2/auth/{}", id);
        try {
            authService.desactivarAuth(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("[v2] Error al desactivar auth: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
