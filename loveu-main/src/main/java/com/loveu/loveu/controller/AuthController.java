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
@RequestMapping("/api/v1/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<AuthDTO> crearAuth(@RequestBody AuthRequestDTO authDTO){
        log.info("POST /api/v1/auth");

        if (authDTO == null) {
            throw new RuntimeException("Los datos de auth son obligatorios");
        }

        if (authDTO.getEmail() == null || authDTO.getEmail().isBlank()) {
            throw new RuntimeException("El email es obligatorio");
        }

        if (!authDTO.getEmail().contains("@") || !authDTO.getEmail().contains(".")) {
            throw new RuntimeException("El email debe tener un formato valido");
        }

        if (authDTO.getPassword() == null || authDTO.getPassword().isBlank()) {
            throw new RuntimeException("La contrasena es obligatoria");
        }

        if (authDTO.getPassword().length() < 6) {
            throw new RuntimeException("La contrasena debe tener al menos 6 caracteres");
        }

        if (authDTO.getUsuarioId() == null) {
            throw new RuntimeException("El usuarioId es obligatorio");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.crearAuth(authDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody AuthRequestDTO authDTO){
        log.info("POST /api/v1/auth/login");

        if (authDTO == null) {
            throw new RuntimeException("Los datos de login son obligatorios");
        }

        if (authDTO.getEmail() == null || authDTO.getEmail().isBlank()) {
            throw new RuntimeException("El email es obligatorio");
        }

        if (authDTO.getPassword() == null || authDTO.getPassword().isBlank()) {
            throw new RuntimeException("La contrasena es obligatoria");
        }

        return ResponseEntity.ok(authService.login(authDTO));
    }

    @GetMapping
    public ResponseEntity<List<AuthDTO>> listarTodos(){
        log.info("GET /api/v1/auth");
        return ResponseEntity.ok(authService.listarTodos());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AuthDTO> buscarPorEmail(@PathVariable String email){
        log.info("GET /api/v1/auth/email/{}", email);
        return ResponseEntity.ok(authService.buscarPorEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarAuth(@PathVariable Integer id){
        log.info("DELETE /api/v1/auth/{}", id);
        authService.desactivarAuth(id);
        return ResponseEntity.noContent().build();
    }
}
