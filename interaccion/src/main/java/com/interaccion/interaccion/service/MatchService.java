package com.interaccion.interaccion.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interaccion.interaccion.dto.MatchDTO;
import com.interaccion.interaccion.model.Match;
import com.interaccion.interaccion.model.MatchStatus;
import com.interaccion.interaccion.repository.MatchRepository;

@Service
public class MatchService {
    private static final Logger log = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private MatchRepository matchRepository;

    public boolean verificarYCrearMatch(Integer perfilAId, Integer perfilBId) {
        log.info("Verificando like mutuo entre perfilA={} y perfilB={}", perfilAId, perfilBId);

        List<Match> matchesExistentes = matchRepository.findByPerfilAIdOrPerfilBId(perfilAId, perfilBId);
        boolean yaExiste = false;

        for (Match m : matchesExistentes) {
            if ((m.getPerfilAId().equals(perfilAId) && m.getPerfilBId().equals(perfilBId)) ||
                (m.getPerfilAId().equals(perfilBId) && m.getPerfilBId().equals(perfilAId))) {
                yaExiste = true;
                break;
            }
        }

        if (yaExiste) {
            log.info("Ya existe un match entre perfilA={} y perfilB={}", perfilAId, perfilBId);
            return false;
        }

        Match match = new Match();
        match.setPerfilAId(perfilAId);
        match.setPerfilBId(perfilBId);

        matchRepository.save(match);
        log.info("Nuevo match creado entre perfilA={} y perfilB={}", perfilAId, perfilBId);
        return true;
    }

    public List<MatchDTO> getMatchesPorPerfil(Integer perfilId) {
        log.info("Obteniendo matches para perfilId={}", perfilId);

        List<MatchDTO> listaDTOs = new ArrayList<>();
        List<Match> matchesReales = matchRepository.findByPerfilAIdOrPerfilBId(perfilId, perfilId);

        for (Match m : matchesReales) {
            listaDTOs.add(toDTO(m));
        }
        return listaDTOs;
    }

    public List<MatchDTO> getTodosLosMatches() {
        log.info("Obteniendo todos los matches");

        List<MatchDTO> listaDTOs = new ArrayList<>();
        List<Match> matchesReales = matchRepository.findAll();

        for (Match m : matchesReales) {
            listaDTOs.add(toDTO(m));
        }
        return listaDTOs;
    }

    public void deshacerMatch(Integer matchId) {
        log.info("Deshaciendo match id={}", matchId);
        Match match = matchRepository.findById(matchId)
            .orElseThrow(() -> new RuntimeException("Match no encontrado: " + matchId));

        match.setStatus(MatchStatus.UNMATCHED);
        matchRepository.save(match);
        log.info("Match id={} marcado como UNMATCHED", matchId);
    }

    private MatchDTO toDTO(Match m) {
        MatchDTO dto = new MatchDTO();
        dto.setPerfilAId(m.getPerfilAId());
        dto.setPerfilBId(m.getPerfilBId());
        dto.setMatchedAt(m.getMatchedAt());
        return dto;
    }
}
