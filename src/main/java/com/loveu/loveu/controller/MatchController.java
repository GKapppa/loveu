package com.loveu.loveu.controller;

import com.loveu.loveu.dto.MatchDTO;
import com.loveu.loveu.model.MatchStatus;
import com.loveu.loveu.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private static final Logger log = LoggerFactory.getLogger(MatchController.class);
    private final MatchService matchService;

    @PostMapping("/verificar")
    public ResponseEntity<Boolean> verificarYCrearMatch(
        @RequestParam Integer perfilAId, @RequestParam Integer perfilBId) {
        log.info("POST /api/matches/verificar perfilAId={} perfilBId={}", perfilAId, perfilBId);
        return ResponseEntity.ok(matchService.verificarYCrearMatch(perfilAId, perfilBId));
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<MatchDTO>> getMatchesPorPerfil(@PathVariable Integer perfilId) {
        log.info("GET /api/matches/perfil/{}", perfilId);
        return ResponseEntity.ok(matchService.getMatchesPorPerfil(perfilId));
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getTodosLosMatches() {
        log.info("GET /api/matches");
        return ResponseEntity.ok(matchService.getTodosLosMatches());
    }

    @PatchMapping("/{id}/deshacer")
    public ResponseEntity<Void> deshacerMatch(@PathVariable Integer id) {
        log.info("PATCH /api/matches/{}/deshacer", id);
        matchService.deshacerMatch(id);
        return ResponseEntity.noContent().build();
    }
}