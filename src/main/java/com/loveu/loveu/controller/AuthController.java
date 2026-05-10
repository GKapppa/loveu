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
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    // Recibe password, pero la respuesta nunca lo devuelve.
    @PostMapping
    public ResponseEntity<AuthDTO> crearAuth(@RequestBody AuthRequestDTO authDTO){
        log.info("POST /api/auth");
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.crearAuth(authDTO));
    }

    // Login basico para el proyecto, sin token todavia.
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody AuthRequestDTO authDTO){
        log.info("POST /api/auth/login");
        return ResponseEntity.ok(authService.login(authDTO));
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
