package com.loveu.loveu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.SwipeDTO;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.model.Swipe;
import com.loveu.loveu.repository.PerfilRepository;
import com.loveu.loveu.repository.SwipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SwipeService {

    private final SwipeRepository swipeRepository;
    private final PerfilRepository perfilRepository;

    // Registra un swipe realizado por un perfil hacia otro perfil.
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
                .id(swipe.getId())
                .perfilOrigenId(swipe.getPerfilOrigen().getId())
                .perfilDestinoId(swipe.getPerfilDestino().getId())
                .decision(swipe.getDecision())
                .fecha(swipe.getFecha())
                .activo(swipe.isActivo())
                .build();
    }

    // Obtiene todos los swipes registrados.
    public List<Swipe> obtenerTodos() {
        return swipeRepository.findAll();
    }

    // Obtiene todos los swipes realizados por un perfil origen.
    public List<Swipe> obtenerPorPerfilOrigen(Integer perfilOrigenId) {
        return swipeRepository.findByPerfilOrigenId(perfilOrigenId);
    }

    // Obtiene todos los swipes recibidos por un perfil destino.
    public List<Swipe> obtenerPorPerfilDestino(Integer perfilDestinoId) {
        return swipeRepository.findByPerfilDestinoId(perfilDestinoId);
    }

    // Elimina un swipe por su id.
    public void eliminarSwipe(Integer id) {
        swipeRepository.deleteById(id);
    }
}