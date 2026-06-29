package com.usuario.usuarios.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import com.usuario.usuarios.assembler.UsuarioModelAssembler;
import com.usuario.usuarios.dto.UsuarioDTO;
import com.usuario.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// GlobalExceptionHandler atrapa todo, ya no necesito try-catch aca
@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios del sistema")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos o usuario ya existe")
    })
    @PostMapping
    public ResponseEntity<EntityModel<UsuarioDTO>> crearUsuario(@Valid @RequestBody UsuarioDTO dto) {
        log.info("POST /api/v2/usuarios");
        UsuarioDTO creado = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios activos del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> obtenerTodos() {
        log.info("GET /api/v2/usuarios");
        List<EntityModel<UsuarioDTO>> lista = usuarioService.obtenerTodos()
                .stream().map(assembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario especifico segun su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerPorId(
            @Parameter(description = "ID del usuario") @PathVariable Integer id) {
        log.info("GET /api/v2/usuarios/{}", id);
        UsuarioDTO dto = usuarioService.obtenerTodos().stream()
                .filter(u -> u.getUsuarioId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> actualizarUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO dto) {
        log.info("PUT /api/v2/usuarios/{}", id);
        UsuarioDTO actualizado = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @Operation(summary = "Eliminar usuario", description = "Desactiva un usuario (soft delete)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Integer id) {
        log.info("DELETE /api/v2/usuarios/{}", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
