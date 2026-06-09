package com.interaccion.interaccion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interaccion.interaccion.dto.SwipeDTO;
import com.interaccion.interaccion.model.DecisionSwipe;
import com.interaccion.interaccion.model.Swipe;
import com.interaccion.interaccion.repository.SwipeRepository;

@Service
public class SwipeService {

    private static final Logger log = LoggerFactory.getLogger(SwipeService.class);

    @Autowired
    private SwipeRepository swipeRepository;

    public SwipeDTO crearSwipe(Integer perfilOrigenId, Integer perfilDestinoId, String decision) {
        log.info("[v2] Creando swipe: origen={} destino={} decision={}", perfilOrigenId, perfilDestinoId, decision);

        if (perfilOrigenId.equals(perfilDestinoId)) {
            throw new RuntimeException("No puedes swipetearte a ti mismo");
        }

        boolean yaExiste = swipeRepository.existsByPerfilOrigenIdAndPerfilDestinoId(perfilOrigenId, perfilDestinoId);
        if (yaExiste) {
            throw new RuntimeException("Ya existe un swipe de este perfil origen hacia este destino");
        }

        Swipe swipe = Swipe.builder()
            .perfilOrigenId(perfilOrigenId)
            .perfilDestinoId(perfilDestinoId)
            .decision(DecisionSwipe.valueOf(decision.toUpperCase()))
            .build();

        swipe = swipeRepository.save(swipe);
        return toDTO(swipe);
    }

    public List<SwipeDTO> getTodos() {
        log.info("[v2] Obteniendo todos los swipes");
        return swipeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<SwipeDTO> getPorOrigen(Integer perfilOrigenId) {
        log.info("[v2] Obteniendo swipes por origen={}", perfilOrigenId);
        return swipeRepository.findByPerfilOrigenId(perfilOrigenId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<SwipeDTO> getPorDestino(Integer perfilDestinoId) {
        log.info("[v2] Obteniendo swipes por destino={}", perfilDestinoId);
        return swipeRepository.findByPerfilDestinoId(perfilDestinoId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SwipeDTO toDTO(Swipe s) {
        return SwipeDTO.builder()
            .id(s.getId())
            .perfilOrigenId(s.getPerfilOrigenId())
            .perfilDestinoId(s.getPerfilDestinoId())
            .decision(s.getDecision().name())
            .fecha(s.getFecha())
            .build();
    }
}
