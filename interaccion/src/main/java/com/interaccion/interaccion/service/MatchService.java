package com.loveu.loveu.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.MatchDTO;
import com.loveu.loveu.model.Match;
import com.loveu.loveu.model.MatchStatus;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.repository.MatchRepository;
import com.loveu.loveu.repository.PerfilRepository;

@Service
public class MatchService {
    private static final Logger log = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public boolean verificarYCrearMatch(Integer perfilAId, Integer perfilBId) {
        log.info("Verificando like mutuo entre perfilA={} y perfilB={}", perfilAId, perfilBId);

        List<Match> matchesExistentes = matchRepository.findByPerfilAIdOrPerfilBId(perfilAId, perfilBId);
        boolean yaExiste = false;

        for (Match m : matchesExistentes) {
            if ((m.getPerfilA().getId().equals(perfilAId) && m.getPerfilB().getId().equals(perfilBId)) ||
                (m.getPerfilA().getId().equals(perfilBId) && m.getPerfilB().getId().equals(perfilAId))) {
                yaExiste = true;
                break;
            }
        }

        if (yaExiste) {
            log.info("Ya existe un match entre perfilA={} y perfilB={}", perfilAId, perfilBId);
            return false;
        }

        Perfil perfilA = perfilRepository.findById(perfilAId)
            .orElseThrow(() -> new RuntimeException("Perfil A no encontrado: " + perfilAId));

        Perfil perfilB = perfilRepository.findById(perfilBId)
            .orElseThrow(() -> new RuntimeException("Perfil B no encontrado: " + perfilBId));

        Match match = new Match();
        match.setPerfilA(perfilA);
        match.setPerfilB(perfilB);

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
        dto.setPerfilAId(m.getPerfilA().getId());
        dto.setPerfilBId(m.getPerfilB().getId());
        dto.setMatchedAt(m.getMatchedAt());
        return dto;
    }
}