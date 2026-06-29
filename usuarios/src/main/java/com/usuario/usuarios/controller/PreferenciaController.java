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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// GlobalExceptionHandler se encarga de todo, ya no ocupo try-catch
@RestController
@RequestMapping("/api/v2/preferencias")
@Tag(name = "Preferencias", description = "Filtros de busqueda para matching")
public class PreferenciaController {

    private static final Logger log = LoggerFactory.getLogger(PreferenciaController.class);

    @Autowired
    private PreferenciaService preferenciaService;

    @Operation(summary = "Crear preferencia", description = "Registra los filtros de busqueda (edad, distancia, altura, genero) para un perfil")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Preferencia creada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos o perfil no existe"),
        @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @PostMapping
    public ResponseEntity<PreferenciaDTO> crearPreferencia(
            @Parameter(description = "DTO con filtros de preferencia") @Valid @RequestBody PreferenciaDTO dto) {
        log.info("POST /api/v2/preferencias");
        PreferenciaDTO creado = preferenciaService.crearPreferencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Listar preferencias", description = "Obtiene todas las preferencias activas del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de preferencias")
    })
    @GetMapping
    public ResponseEntity<List<PreferenciaDTO>> listarTodas() {
        log.info("GET /api/v2/preferencias");
        return ResponseEntity.ok(preferenciaService.listarTodas());
    }

    @Operation(summary = "Buscar preferencia por ID", description = "Obtiene una preferencia especifica segun su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Preferencia encontrada"),
        @ApiResponse(responseCode = "404", description = "Preferencia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PreferenciaDTO> buscarPorId(
            @Parameter(description = "ID de la preferencia") @PathVariable Integer id) {
        log.info("GET /api/v2/preferencias/{}", id);
        return ResponseEntity.ok(preferenciaService.buscarPorId(id));
    }

    @Operation(summary = "Buscar preferencia por perfil", description = "Obtiene la preferencia asociada a un perfil especifico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Preferencia encontrada"),
        @ApiResponse(responseCode = "404", description = "Preferencia no encontrada para ese perfil")
    })
    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<PreferenciaDTO> buscarPorPerfil(
            @Parameter(description = "ID del perfil") @PathVariable Integer perfilId) {
        log.info("GET /api/v2/preferencias/perfil/{}", perfilId);
        return ResponseEntity.ok(preferenciaService.buscarPorPerfil(perfilId));
    }

    @Operation(summary = "Actualizar preferencia", description = "Modifica los filtros de una preferencia existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Preferencia actualizada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Preferencia no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PreferenciaDTO> actualizarPreferencia(
            @Parameter(description = "ID de la preferencia") @PathVariable Integer id,
            @Valid @RequestBody PreferenciaDTO dto) {
        log.info("PUT /api/v2/preferencias/{}", id);
        return ResponseEntity.ok(preferenciaService.actualizarPreferencia(id, dto));
    }

    @Operation(summary = "Desactivar preferencia", description = "Desactiva una preferencia (soft delete)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Preferencia desactivada"),
        @ApiResponse(responseCode = "404", description = "Preferencia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarPreferencia(
            @Parameter(description = "ID de la preferencia") @PathVariable Integer id) {
        log.info("DELETE /api/v2/preferencias/{}", id);
        preferenciaService.desactivarPreferencia(id);
        return ResponseEntity.noContent().build();
    }
}
