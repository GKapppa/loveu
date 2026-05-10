package com.loveu.loveu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.SwipeDTO;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.model.Swipe;
import com.loveu.loveu.repository.PerfilRepository;
import com.loveu.loveu.repository.SwipeRepository;

@Service
public class SwipeService {

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public SwipeDTO registrarSwipe(SwipeDTO dto) {

        if (swipeRepository.existsByPerfilOrigenIdAndPerfilDestinoId(
                dto.getPerfilOrigenId(),
                dto.getPerfilDestinoId())) {

            throw new RuntimeException("Ya existe un swipe entre estos perfiles");
        }

        Perfil perfilOrigen = perfilRepository.findById(dto.getPerfilOrigenId())
                .orElseThrow(() -> new RuntimeException("Perfil origen no encontrado"));

        Perfil perfilDestino = perfilRepository.findById(dto.getPerfilDestinoId())
                .orElseThrow(() -> new RuntimeException("Perfil destino no encontrado"));

        Swipe swipe = Swipe.builder()
                .perfilOrigen(perfilOrigen)
                .perfilDestino(perfilDestino)
                .decision(dto.getDecision())
                .activo(true)
                .build();

        swipe = swipeRepository.save(swipe);

        return SwipeDTO.builder()
                .perfilOrigenId(swipe.getPerfilOrigen().getId())
                .perfilDestinoId(swipe.getPerfilDestino().getId())
                .decision(swipe.getDecision())
                .fecha(swipe.getFecha())
                .build();
    }

    public List<SwipeDTO> obtenerTodos() {
        return swipeRepository.findAll().stream().map(this::toDTO).toList();
    }

    public List<SwipeDTO> obtenerPorPerfilOrigen(Integer perfilOrigenId) {
        return swipeRepository.findByPerfilOrigenId(perfilOrigenId).stream().map(this::toDTO).toList();
    }

    public List<SwipeDTO> obtenerPorPerfilDestino(Integer perfilDestinoId) {
        return swipeRepository.findByPerfilDestinoId(perfilDestinoId).stream().map(this::toDTO).toList();
    }

    public void eliminarSwipe(Integer id) {
        swipeRepository.deleteById(id);
    }

    private SwipeDTO toDTO(Swipe swipe) {
        return SwipeDTO.builder()
                .perfilOrigenId(swipe.getPerfilOrigen().getId())
                .perfilDestinoId(swipe.getPerfilDestino().getId())
                .decision(swipe.getDecision())
                .fecha(swipe.getFecha())
                .build();
    }
}
