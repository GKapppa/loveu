package com.loveu.loveu.service;

import java.util.List;
import java.util.stream.Collectors;

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

// @Service marca esta clase como la capa donde vive la logica de negocio.
@Service
public class MatchService {
    // Logger para registrar pasos importantes sin usar System.out.println.
    private static final Logger log = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    /**
     * Crea un match entre dos perfiles si no existe ya uno.
     * Retorna true si el match fue creado, false si ya existía.
     */
    public boolean verificarYCrearMatch(Integer perfilAId, Integer perfilBId) {
        log.info("Verificando like mutuo entre perfilA={} y perfilB={}", perfilAId, perfilBId);

        // Revisar si ya existe un match entre estos dos perfiles
        boolean yaExiste = matchRepository
            // Spring Data crea la consulta a partir del nombre del metodo.
            .findByPerfilAIdOrPerfilBId(perfilAId, perfilBId)
            // stream permite recorrer la lista y aplicar filtros/transformaciones.
            .stream()
            // anyMatch retorna true si al menos un elemento cumple la condicion.
            .anyMatch(m ->
                (m.getPerfilA().getId().equals(perfilAId) && m.getPerfilB().getId().equals(perfilBId)) ||
                (m.getPerfilA().getId().equals(perfilBId) && m.getPerfilB().getId().equals(perfilAId))
            );

        if (yaExiste) {
            log.info("Ya existe un match entre perfilA={} y perfilB={}", perfilAId, perfilBId);
            return false;
        }

        // findById devuelve Optional; orElseThrow evita trabajar con datos inexistentes.
        Perfil perfilA = perfilRepository.findById(perfilAId)
            .orElseThrow(() -> new RuntimeException("Perfil A no encontrado: " + perfilAId));

        Perfil perfilB = perfilRepository.findById(perfilBId)
            .orElseThrow(() -> new RuntimeException("Perfil B no encontrado: " + perfilBId));

        // builder arma la entidad de forma legible antes de guardarla.
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
            // map(this::toDTO) convierte cada Match a MatchDTO.
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
        // En vez de borrar el registro, se cambia su estado.
        match.setStatus(MatchStatus.UNMATCHED);
        matchRepository.save(match);
        log.info("Match id={} marcado como UNMATCHED", matchId);
    }

    // Convierte la entidad JPA a DTO para responder solo los datos necesarios.
    private MatchDTO toDTO(Match m) {
        return MatchDTO.builder()
            .id(m.getId())
            .perfilAId(m.getPerfilA().getId())
            .perfilBId(m.getPerfilB().getId())
            .status(m.getStatus())
            .matchedAt(m.getMatchedAt())
            .build();
    }
}
