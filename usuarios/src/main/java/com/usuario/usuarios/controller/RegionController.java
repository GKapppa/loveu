package com.usuario.usuarios.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuarios.dto.RegionDTO;
import com.usuario.usuarios.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/regiones")
@Tag(name = "Regiones", description = "Regiones geograficas de Chile")
public class RegionController {

    private static final Logger log = LoggerFactory.getLogger(RegionController.class);

    @Autowired
    private RegionService regionService;

    @Operation(summary = "Listar regiones", description = "Obtiene todas las regiones disponibles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de regiones")
    })
    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarTodo() {
        log.info("GET /api/v2/regiones");
        return ResponseEntity.ok(regionService.listarTodo());
    }

    @Operation(summary = "Buscar region por ID", description = "Obtiene una region especifica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Region encontrada"),
        @ApiResponse(responseCode = "404", description = "Region no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> buscarPorId(
            @Parameter(description = "ID de la region") @PathVariable Integer id) {
        log.info("GET /api/v2/regiones/{}", id);
        return ResponseEntity.ok(regionService.buscarPorId(id));
    }
}
