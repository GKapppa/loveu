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

// Las excepciones las maneja GlobalExceptionHandler, ya no necesito try-catch
@RestController
@RequestMapping("/api/v2/matches")
public class MatchController {
    private static final Logger log = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchService matchService;

    @PostMapping("/verificar")
    public ResponseEntity<Boolean> verificarYCrearMatch(@RequestParam Integer perfilAId,
            @RequestParam Integer perfilBId) {
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

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<MatchDTO>> getMatchesPorPerfil(@PathVariable Integer perfilId) {
        log.info("GET /api/v2/matches/perfil/{}", perfilId);
        List<MatchDTO> lista = matchService.getMatchesPorPerfil(perfilId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getTodosLosMatches() {
        log.info("GET /api/v2/matches");
        List<MatchDTO> lista = matchService.getTodosLosMatches();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deshacer")
    public ResponseEntity<Void> deshacerMatch(@PathVariable Integer id) {
        log.info("PATCH /api/v2/matches/{}/deshacer", id);
        matchService.deshacerMatch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
