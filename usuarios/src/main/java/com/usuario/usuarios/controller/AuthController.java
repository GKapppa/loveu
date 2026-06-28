package com.usuario.usuarios.controller;

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

// El @RestControllerAdvice maneja los errores, ya no tengo que preocuparme
@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<AuthDTO> crearAuth(@RequestParam Integer usuarioId,
            @Valid @RequestBody AuthRequestDTO authDTO) {
        log.info("[v2] POST /api/v2/auth para usuarioId={}", usuarioId);
        AuthDTO dto = authService.crearAuth(usuarioId, authDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody AuthRequestDTO authDTO) {
        log.info("[v2] POST /api/v2/auth/login");
        return ResponseEntity.ok(authService.login(authDTO));
    }

    @GetMapping
    public ResponseEntity<java.util.List<AuthDTO>> listarTodos() {
        log.info("[v2] GET /api/v2/auth");
        return ResponseEntity.ok(authService.listarTodos());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AuthDTO> buscarPorEmail(@PathVariable String email) {
        log.info("[v2] GET /api/v2/auth/email/{}", email);
        return ResponseEntity.ok(authService.buscarPorEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarAuth(@PathVariable Integer id) {
        log.info("[v2] DELETE /api/v2/auth/{}", id);
        authService.desactivarAuth(id);
        return ResponseEntity.noContent().build();
    }
}
