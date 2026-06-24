package com.interaccion.interaccion.service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private InteraccionValidaciones validaciones;

    public boolean verificarYCrearMatch(Integer perfilAId, Integer perfilBId) {
        log.info("Verificando like mutuo entre perfilA={} y perfilB={}", perfilAId, perfilBId);
        validaciones.validarNoSelfMatch(perfilAId, perfilBId);

        List<Match> existentes = matchRepository.findByPerfilAIdOrPerfilBId(perfilAId, perfilBId);
        for (Match m : existentes) {
            if ((m.getPerfilAId().equals(perfilAId) && m.getPerfilBId().equals(perfilBId))
                    || (m.getPerfilAId().equals(perfilBId) && m.getPerfilBId().equals(perfilAId))) {
                log.info("Ya existe un match entre perfilA={} y perfilB={}", perfilAId, perfilBId);
                return false;
            }
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
        return matchRepository.findByPerfilAIdOrPerfilBId(perfilId, perfilId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MatchDTO> getTodosLosMatches() {
        log.info("Obteniendo todos los matches");
        return matchRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void deshacerMatch(Integer matchId) {
        log.info("Deshaciendo match id={}", matchId);
        Match match = validaciones.validarMatchExiste(matchId);
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
