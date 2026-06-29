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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.usuario.usuarios.dto.AuthDTO;
import com.usuario.usuarios.dto.AuthRequestDTO;
import com.usuario.usuarios.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// El @RestControllerAdvice maneja los errores, ya no tengo que preocuparme
@RestController
@RequestMapping("/api/v2/auth")
@Tag(name = "Auth", description = "Autenticacion y registro de usuarios")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Operation(summary = "Crear auth", description = "Asocia un email y password a un usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Auth creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Email duplicado o usuario no existe")
    })
    @PostMapping
    public ResponseEntity<AuthDTO> crearAuth(
            @Parameter(description = "ID del usuario asociado") @RequestParam Integer usuarioId,
            @Valid @RequestBody AuthRequestDTO authDTO) {
        log.info("[v2] POST /api/v2/auth para usuarioId={}", usuarioId);
        AuthDTO dto = authService.crearAuth(usuarioId, authDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Iniciar sesion", description = "Verifica credenciales y retorna el auth si son correctas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody AuthRequestDTO authDTO) {
        log.info("[v2] POST /api/v2/auth/login");
        return ResponseEntity.ok(authService.login(authDTO));
    }

    @Operation(summary = "Listar auths", description = "Obtiene todos los registros de autenticacion")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de auths")
    })
    @GetMapping
    public ResponseEntity<List<AuthDTO>> listarTodos() {
        log.info("[v2] GET /api/v2/auth");
        return ResponseEntity.ok(authService.listarTodos());
    }

    @Operation(summary = "Buscar por email", description = "Busca un auth segun su email")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Auth encontrado"),
        @ApiResponse(responseCode = "404", description = "Auth no encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<AuthDTO> buscarPorEmail(
            @Parameter(description = "Email del auth") @PathVariable String email) {
        log.info("[v2] GET /api/v2/auth/email/{}", email);
        return ResponseEntity.ok(authService.buscarPorEmail(email));
    }

    @Operation(summary = "Desactivar auth", description = "Desactiva un auth (soft delete)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Auth desactivado"),
        @ApiResponse(responseCode = "404", description = "Auth no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarAuth(
            @Parameter(description = "ID del auth") @PathVariable Integer id) {
        log.info("[v2] DELETE /api/v2/auth/{}", id);
        authService.desactivarAuth(id);
        return ResponseEntity.noContent().build();
    }
}
