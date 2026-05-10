package com.loveu.loveu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.PerfilDTO;
import com.loveu.loveu.model.Comuna;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.model.Usuario;
import com.loveu.loveu.repository.ComunaRepository;
import com.loveu.loveu.repository.PerfilRepository;
import com.loveu.loveu.repository.UsuarioRepository;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    // Crea el perfil visible de un usuario dentro de la aplicacion.
    public PerfilDTO crearPerfil(PerfilDTO dto) {

        if (perfilRepository.findByUsuarioId(dto.getUsuarioId()).isPresent()) {
            throw new RuntimeException("Este usuario ya tiene un perfil creado");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comuna comuna = comunaRepository.findById(dto.getComunaId())
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));

        Perfil perfil = Perfil.builder()
                .nombreVisible(dto.getNombreVisible())
                .biografia(dto.getBiografia())
                .ocupacion(dto.getOcupacion())
                .alturaCm(dto.getAlturaCm())
                .activo(true)
                .usuario(usuario)
                .comuna(comuna)
                .build();

        perfil = perfilRepository.save(perfil);

        return toDTO(perfil);
    }

    // Obtiene todos los perfiles registrados.
    public List<PerfilDTO> getTodos() {
        return perfilRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PerfilDTO getPorId(Integer id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        return toDTO(perfil);
    }

    // Obtiene el perfil asociado a un usuario.
    public PerfilDTO getPorUsuario(Integer usuarioId) {
        Perfil perfil = perfilRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado para este usuario"));

        return toDTO(perfil);
    }

    // Obtiene todos los perfiles que pertenecen a una comuna.
    public List<PerfilDTO> getPorComuna(Integer comunaId) {
        return perfilRepository.findByComunaId(comunaId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Actualiza los datos publicos del perfil.
    public PerfilDTO actualizarPerfil(Integer id, PerfilDTO dto) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        Comuna comuna = comunaRepository.findById(dto.getComunaId())
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));

        perfil.setNombreVisible(dto.getNombreVisible());
        perfil.setBiografia(dto.getBiografia());
        perfil.setOcupacion(dto.getOcupacion());
        perfil.setAlturaCm(dto.getAlturaCm());
        perfil.setComuna(comuna);

        perfil = perfilRepository.save(perfil);

        return toDTO(perfil);
    }

    // Desactiva el perfil sin eliminarlo fisicamente de la base de datos.
    public void desactivarPerfil(Integer id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        perfil.setActivo(false);
        perfilRepository.save(perfil);
    }

    // Elimina un perfil por su id.
    public void eliminarPerfil(Integer id) {
        perfilRepository.deleteById(id);
    }

    private PerfilDTO toDTO(Perfil perfil) {
        return PerfilDTO.builder()
                .id(perfil.getId())
                .nombreVisible(perfil.getNombreVisible())
                .biografia(perfil.getBiografia())
                .ocupacion(perfil.getOcupacion())
                .alturaCm(perfil.getAlturaCm())
                .activo(perfil.isActivo())
                .usuarioId(perfil.getUsuario().getId())
                .comunaId(perfil.getComuna().getId())
                .build();
    }
}
