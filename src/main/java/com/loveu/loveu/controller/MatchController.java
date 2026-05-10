package com.loveu.loveu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.MatchDTO;
import com.loveu.loveu.service.MatchService;

// Controlador REST: recibe peticiones HTTP y devuelve respuestas JSON.
@RestController
// Ruta base para todos los endpoints de matches.
@RequestMapping("/api/matches")
public class MatchController {
    // Logger usado para registrar en consola que endpoint se ejecuto.
    private static final Logger log = LoggerFactory.getLogger(MatchController.class);
    @Autowired
    private MatchService matchService;

    // @RequestParam lee parametros enviados en la URL, por ejemplo ?perfilAId=1.
    @PostMapping("/verificar")
    public ResponseEntity<Boolean> verificarYCrearMatch(
        @RequestParam Integer perfilAId, @RequestParam Integer perfilBId) {
        log.info("POST /api/matches/verificar perfilAId={} perfilBId={}", perfilAId, perfilBId);
        // ResponseEntity.ok responde HTTP 200 con el resultado del service.
        return ResponseEntity.ok(matchService.verificarYCrearMatch(perfilAId, perfilBId));
    }

    @GetMapping("/perfil/{perfilId}")
    // @PathVariable obtiene el valor que viene dentro de la ruta.
    public ResponseEntity<List<MatchDTO>> getMatchesPorPerfil(@PathVariable Integer perfilId) {
        log.info("GET /api/matches/perfil/{}", perfilId);
        return ResponseEntity.ok(matchService.getMatchesPorPerfil(perfilId));
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getTodosLosMatches() {
        log.info("GET /api/matches");
        return ResponseEntity.ok(matchService.getTodosLosMatches());
    }

    // PATCH se usa para modificar parcialmente un recurso existente.
    @PatchMapping("/{id}/deshacer")
    public ResponseEntity<Void> deshacerMatch(@PathVariable Integer id) {
        log.info("PATCH /api/matches/{}/deshacer", id);
        matchService.deshacerMatch(id);
        return ResponseEntity.noContent().build();
    }
}
