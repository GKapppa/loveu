package com.interaccion.interaccion.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interaccion.interaccion.dto.MatchDTO;
import com.interaccion.interaccion.service.MatchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// Las excepciones las maneja GlobalExceptionHandler, ya no necesito try-catch
@RestController
@RequestMapping("/api/v2/matches")
@Tag(name = "Matches", description = "Match entre perfiles")
public class MatchController {
    private static final Logger log = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchService matchService;

    @Operation(summary = "Verificar y crear match", description = "Verifica si hay match mutuo entre dos perfiles y lo crea si corresponde")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Match verificado"),
        @ApiResponse(responseCode = "400", description = "Perfiles invalidos o match no posible")
    })
    @PostMapping("/verificar")
    public ResponseEntity<Boolean> verificarYCrearMatch(
            @Parameter(description = "ID del perfil A") @RequestParam Integer perfilAId,
            @Parameter(description = "ID del perfil B") @RequestParam Integer perfilBId) {
        log.info("POST /api/v2/matches/verificar perfilAId={} perfilBId={}", perfilAId, perfilBId);

        if (perfilAId == null || perfilBId == null) {
            throw new RuntimeException("Los dos perfiles son obligatorios");
        }

        if (perfilAId.equals(perfilBId)) {
            throw new RuntimeException("No se puede crear match con el mismo perfil");
        }

        Boolean resultado = matchService.verificarYCrearMatch(perfilAId, perfilBId);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @Operation(summary = "Listar matches por perfil", description = "Obtiene todos los matches activos de un perfil")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de matches")
    })
    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<MatchDTO>> getMatchesPorPerfil(
            @Parameter(description = "ID del perfil") @PathVariable Integer perfilId) {
        log.info("GET /api/v2/matches/perfil/{}", perfilId);
        List<MatchDTO> lista = matchService.getMatchesPorPerfil(perfilId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Listar todos los matches", description = "Obtiene todos los matches del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de matches")
    })
    @GetMapping
    public ResponseEntity<List<MatchDTO>> getTodosLosMatches() {
        log.info("GET /api/v2/matches");
        List<MatchDTO> lista = matchService.getTodosLosMatches();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Deshacer match", description = "Marca un match como unmatched (ya no es match)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Match deshecho"),
        @ApiResponse(responseCode = "404", description = "Match no encontrado")
    })
    @PatchMapping("/{id}/deshacer")
    public ResponseEntity<Void> deshacerMatch(
            @Parameter(description = "ID del match") @PathVariable Integer id) {
        log.info("PATCH /api/v2/matches/{}/deshacer", id);
        matchService.deshacerMatch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
