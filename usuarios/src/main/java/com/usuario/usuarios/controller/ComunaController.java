package com.usuario.usuarios.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuarios.dto.ComunaDTO;
import com.usuario.usuarios.service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/comunas")
@Tag(name = "Comunas", description = "Comunas asociadas a regiones")
public class ComunaController {
    private static final Logger log = LoggerFactory.getLogger(ComunaController.class);

    @Autowired
    private ComunaService comunaService;

    @Operation(summary = "Listar comunas", description = "Obtiene todas las comunas disponibles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de comunas")
    })
    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarTodo(){
        log.info("GET /api/v2/comunas");
        return ResponseEntity.ok(comunaService.listarTodo());
    }
}
