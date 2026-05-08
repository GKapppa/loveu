package com.loveu.loveu.service;

import com.loveu.loveu.dto.MatchDTO;
import com.loveu.loveu.model.Match;
import com.loveu.loveu.model.MatchStatus;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.repository.MatchRepository;
import com.loveu.loveu.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {
    private static final Logger log = LoggerFactory.getLogger(MatchService.class);

    private final MatchRepository matchRepository;
    private final PerfilRepository perfilRepository;

    /**
     * Crea un match entre dos perfiles si no existe ya uno.
     * Retorna true si el match fue creado, false si ya existía.
     */
    public boolean verificarYCrearMatch(Integer perfilAId, Integer perfilBId) {
        log.info("Verificando like mutuo entre perfilA={} y perfilB={}", perfilAId, perfilBId);

        // Revisar si ya existe un match entre estos dos perfiles
        boolean yaExiste = matchRepository
            .findByPerfilAIdOrPerfilBId(perfilAId, perfilBId)
            .stream()
            .anyMatch(m ->
                (m.getPerfilA().getId().equals(perfilAId) && m.getPerfilB().getId().equals(perfilBId)) ||
                (m.getPerfilA().getId().equals(perfilBId) && m.getPerfilB().getId().equals(perfilAId))
            );

        if (yaExiste) {
            log.info("Ya existe un match entre perfilA={} y perfilB={}", perfilAId, perfilBId);
            return false;
        }

        Perfil perfilA = perfilRepository.findById(perfilAId)
            .orElseThrow(() -> new RuntimeException("Perfil A no encontrado: " + perfilAId));

        Perfil perfilB = perfilRepository.findById(perfilBId)
            .orElseThrow(() -> new RuntimeException("Perfil B no encontrado: " + perfilBId));

        Match match = Match.builder()
            .perfilA(perfilA)
            .perfilB(perfilB)
            // status y matchedAt se asignan automáticamente en @PrePersist
            .build();

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
        return matchRepository.findAll()
            .stream().map(this::toDTO).collect(Collectors.toList());
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
        return MatchDTO.builder()
            .id(m.getId())
            .perfilA(m.getPerfilA().getId())
            .perfilB(m.getPerfilB().getId())
            .status(m.getStatus())
            .matchedAt(m.getMatchedAt())
            .build();
    }
}