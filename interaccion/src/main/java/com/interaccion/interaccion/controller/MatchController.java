package com.interaccion.interaccion.controller;

import java.util.List;
import java.util.Map;
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

@RestController
@RequestMapping("/api/v2/matches")
public class MatchController {
    private static final Logger log = LoggerFactory.getLogger(MatchController.class);

    @Autowired
    private MatchService matchService;

    @PostMapping("/verificar")
    public ResponseEntity<?> verificarYCrearMatch(@RequestParam Integer perfilAId, @RequestParam Integer perfilBId) {
        log.info("POST /api/v1/matches/verificar perfilAId={} perfilBId={}", perfilAId, perfilBId);
        try {
            if (perfilAId == null || perfilBId == null) {
                throw new RuntimeException("Los dos perfiles son obligatorios");
            }

            if (perfilAId.equals(perfilBId)) {
                throw new RuntimeException("No se puede crear match con el mismo perfil");
            }

            Boolean resultado = matchService.verificarYCrearMatch(perfilAId, perfilBId);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<MatchDTO>> getMatchesPorPerfil(@PathVariable Integer perfilId) {
        log.info("GET /api/v1/matches/perfil/{}", perfilId);
        List<MatchDTO> lista = matchService.getMatchesPorPerfil(perfilId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getTodosLosMatches() {
        log.info("GET /api/v1/matches");
        List<MatchDTO> lista = matchService.getTodosLosMatches();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deshacer")
    public ResponseEntity<?> deshacerMatch(@PathVariable Integer id) {
        log.info("PATCH /api/v1/matches/{}/deshacer", id);
        try {
            matchService.deshacerMatch(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
