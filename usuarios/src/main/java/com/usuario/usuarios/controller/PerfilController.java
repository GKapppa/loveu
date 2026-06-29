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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuarios.dto.PerfilDTO;
import com.usuario.usuarios.service.PerfilService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Las excepciones las atrapa GlobalExceptionHandler, ya no necesito try-catch aca
@RestController
@RequestMapping("/api/v2/perfiles")
@Tag(name = "Perfiles", description = "Perfiles de usuario en la app de citas")
public class PerfilController {
    private static final Logger log = LoggerFactory.getLogger(PerfilController.class);

    @Autowired
    private PerfilService perfilService;

    @Operation(summary = "Crear perfil", description = "Crea un nuevo perfil asociado a un usuario y comuna")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Perfil creado"),
        @ApiResponse(responseCode = "400", description = "Usuario ya tiene perfil o datos invalidos")
    })
    @PostMapping
    public ResponseEntity<PerfilDTO> crearPerfil(
            @Parameter(description = "ID del usuario") @RequestParam Integer usuarioId,
            @Parameter(description = "ID de la comuna") @RequestParam Integer comunaId) {
        log.info("Post /api/v2/perfiles");
        PerfilDTO dto = perfilService.crearPerfil(usuarioId, comunaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Listar perfiles", description = "Obtiene todos los perfiles activos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de perfiles")
    })
    @GetMapping
    public ResponseEntity<List<PerfilDTO>> getTodos() {
        log.info("GET /api/v2/perfiles");
        return ResponseEntity.ok(perfilService.getTodos());
    }

    @Operation(summary = "Buscar perfil por usuario", description = "Obtiene el perfil asociado a un usuario especifico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
        @ApiResponse(responseCode = "404", description = "Perfil no encontrado para ese usuario")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPorId(
            @Parameter(description = "ID del usuario") @PathVariable Integer id) {
        log.info("GET /api/v2/perfiles/{}", id);
        return ResponseEntity.ok(perfilService.getPorUsuario(id));
    }

    @Operation(summary = "Actualizar perfil", description = "Modifica datos del perfil (nombre visible, biografia, ocupacion, altura)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil actualizado"),
        @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(
            @Parameter(description = "ID del perfil") @PathVariable Integer id,
            @Valid @RequestBody PerfilDTO dto) {
        log.info("PUT /api/v2/perfiles/{}", id);
        return ResponseEntity.ok(perfilService.actualizarPerfil(id, dto));
    }

    @Operation(summary = "Desactivar perfil", description = "Desactiva un perfil (soft delete)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Perfil desactivado"),
        @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarPerfil(
            @Parameter(description = "ID del perfil") @PathVariable Integer id) {
        log.info("DELETE /api/v2/perfiles/{}", id);
        perfilService.desactivarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}
