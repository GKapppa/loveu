package com.loveu.loveu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.SwipeDTO;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.model.Swipe;
import com.loveu.loveu.repository.PerfilRepository;
import com.loveu.loveu.repository.SwipeRepository;

// @Service registra esta clase como parte de la logica de negocio de Spring.
@Service
public class SwipeService {

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    // Registra un swipe realizado por un perfil hacia otro perfil.
    public SwipeDTO registrarSwipe(SwipeDTO dto) {

        // Metodo derivado de Spring Data: busca si ya existe ese par origen-destino.
        if (swipeRepository.existsByPerfilOrigenIdAndPerfilDestinoId(
                dto.getPerfilOrigenId(),
                dto.getPerfilDestinoId())) {

            throw new RuntimeException("Ya existe un swipe entre estos perfiles");
        }

        // findById devuelve Optional; orElseThrow lanza error si no encuentra el registro.
        Perfil perfilOrigen = perfilRepository.findById(dto.getPerfilOrigenId())
                .orElseThrow(() -> new RuntimeException("Perfil origen no encontrado"));

        Perfil perfilDestino = perfilRepository.findById(dto.getPerfilDestinoId())
                .orElseThrow(() -> new RuntimeException("Perfil destino no encontrado"));

        // builder() permite crear el objeto de forma clara sin un constructor largo.
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
    public List<SwipeDTO> obtenerTodos() {
        // stream().map(...) transforma cada entidad Swipe en un SwipeDTO.
        return swipeRepository.findAll().stream().map(this::toDTO).toList();
    }

    // Obtiene todos los swipes realizados por un perfil origen.
    public List<SwipeDTO> obtenerPorPerfilOrigen(Integer perfilOrigenId) {
        return swipeRepository.findByPerfilOrigenId(perfilOrigenId).stream().map(this::toDTO).toList();
    }

    // Obtiene todos los swipes recibidos por un perfil destino.
    public List<SwipeDTO> obtenerPorPerfilDestino(Integer perfilDestinoId) {
        return swipeRepository.findByPerfilDestinoId(perfilDestinoId).stream().map(this::toDTO).toList();
    }

    // Elimina un swipe por su id.
    public void eliminarSwipe(Integer id) {
        swipeRepository.deleteById(id);
    }

    // Convierte la entidad JPA a DTO para no exponer relaciones internas en la API.
    private SwipeDTO toDTO(Swipe swipe) {
        return SwipeDTO.builder()
                .id(swipe.getId())
                .perfilOrigenId(swipe.getPerfilOrigen().getId())
                .perfilDestinoId(swipe.getPerfilDestino().getId())
                .decision(swipe.getDecision())
                .fecha(swipe.getFecha())
                .activo(swipe.isActivo())
                .build();
    }
}
