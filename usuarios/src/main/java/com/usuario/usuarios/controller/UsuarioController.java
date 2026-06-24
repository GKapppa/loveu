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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<UsuarioDTO>> crearUsuario(@Valid @RequestBody UsuarioDTO dto) {
        log.info("POST /api/v2/usuarios");
        UsuarioDTO creado = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> obtenerTodos() {
        log.info("GET /api/v2/usuarios");
        List<EntityModel<UsuarioDTO>> lista = usuarioService.obtenerTodos()
                .stream().map(assembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerPorId(@PathVariable Integer id) {
        log.info("GET /api/v2/usuarios/{}", id);
        UsuarioDTO dto = usuarioService.obtenerTodos().stream()
                .filter(u -> u.getUsuarioId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> actualizarUsuario(@PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO dto) {
        log.info("PUT /api/v2/usuarios/{}", id);
        UsuarioDTO actualizado = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        log.info("DELETE /api/v2/usuarios/{}", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
