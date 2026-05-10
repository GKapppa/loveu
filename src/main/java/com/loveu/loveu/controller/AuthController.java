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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.AuthDTO;
import com.loveu.loveu.dto.AuthRequestDTO;
import com.loveu.loveu.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // Logger sirve para ver en consola que endpoint se esta usando.
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    // Recibe password, pero la respuesta nunca lo devuelve.
    @PostMapping
    public ResponseEntity<?> crearAuth(@RequestBody AuthRequestDTO authDTO){
        log.info("POST /api/auth");

        // Validaciones simples antes de intentar guardar.
        if (authDTO == null) {
            return ResponseEntity.badRequest().body("Los datos de auth son obligatorios");
        }

        if (authDTO.getEmail() == null || authDTO.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        if (!authDTO.getEmail().contains("@") || !authDTO.getEmail().contains(".")) {
            return ResponseEntity.badRequest().body("El email debe tener un formato valido");
        }

        if (authDTO.getPassword() == null || authDTO.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("La contrasena es obligatoria");
        }

        if (authDTO.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("La contrasena debe tener al menos 6 caracteres");
        }

        if (authDTO.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body("El usuarioId es obligatorio");
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.crearAuth(authDTO));
        } catch (RuntimeException ex) {
            // Si el service lanza error, lo mostramos como respuesta clara.
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Login basico para el proyecto, sin token todavia.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authDTO){
        log.info("POST /api/auth/login");

        if (authDTO == null) {
            return ResponseEntity.badRequest().body("Los datos de login son obligatorios");
        }

        if (authDTO.getEmail() == null || authDTO.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        if (authDTO.getPassword() == null || authDTO.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("La contrasena es obligatoria");
        }

        try {
            return ResponseEntity.ok(authService.login(authDTO));
        } catch (RuntimeException ex) {
            // Evita mostrar el error completo de Java al cliente.
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthDTO>> listarTodos(){
        log.info("GET /api/auth");
        return ResponseEntity.ok(authService.listarTodos());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AuthDTO> buscarPorEmail(@PathVariable String email){
        log.info("GET /api/auth/email/{}", email);
        return ResponseEntity.ok(authService.buscarPorEmail(email));
    }

    // Se desactiva la cuenta para no perder el historial del usuario.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarAuth(@PathVariable Integer id){
        log.info("DELETE /api/auth/{}", id);
        authService.desactivarAuth(id);
        return ResponseEntity.noContent().build();
    }
}
