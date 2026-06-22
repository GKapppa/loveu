package com.usuario.usuarios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.usuarios.dto.PerfilDTO;
import com.usuario.usuarios.model.Perfil;
import com.usuario.usuarios.repository.ComunaRepository;
import com.usuario.usuarios.repository.PerfilRepository;
import com.usuario.usuarios.repository.UsuarioRepository;

@Service
public class PerfilService {

    private static final Logger log = LoggerFactory.getLogger(PerfilService.class);

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    public PerfilDTO crearPerfil(Integer usuarioId, Integer comunaId) {
        log.info("[v2] Creando perfil para usuarioId={}", usuarioId);

        if (perfilRepository.findByUsuarioId(usuarioId).isPresent()) {
            throw new RuntimeException("Este usuario ya tiene un perfil creado");
        }

        usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuarioId));

        comunaRepository.findById(comunaId)
            .orElseThrow(() -> new RuntimeException("Comuna no encontrada: " + comunaId));

        Perfil perfil = Perfil.builder()
            .usuarioId(usuarioId)
            .comunaId(comunaId)
            .activo(true)
            .build();

        perfil = perfilRepository.save(perfil);
        return toDTO(perfil);
    }

    public List<PerfilDTO> getTodos() {
        log.info("[v2] Obteniendo todos los perfiles");
        return perfilRepository.findByActivoTrue()
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PerfilDTO getPorUsuario(Integer usuarioId) {
        log.info("[v2] Obteniendo perfil de usuarioId={}", usuarioId);
        Perfil perfil = perfilRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Perfil no encontrado para usuario: " + usuarioId));
        return toDTO(perfil);
    }

    public PerfilDTO actualizarPerfil(Integer id, PerfilDTO dto) {
        log.info("[v2] Actualizando perfil id={}", id);
        Perfil perfil = perfilRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Perfil no encontrado: " + id));

        if (dto.getNombreVisible() != null) perfil.setNombreVisible(dto.getNombreVisible());
        if (dto.getBiografia() != null) perfil.setBiografia(dto.getBiografia());
        if (dto.getOcupacion() != null) perfil.setOcupacion(dto.getOcupacion());
        if (dto.getAlturaCm() != null) perfil.setAlturaCm(dto.getAlturaCm());

        perfil = perfilRepository.save(perfil);
        return toDTO(perfil);
    }

    public void desactivarPerfil(Integer id) {
        log.info("[v2] Desactivando perfil id={}", id);
        Perfil perfil = perfilRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Perfil no encontrado: " + id));
        perfil.setActivo(false);
        perfilRepository.save(perfil);
    }

    private PerfilDTO toDTO(Perfil p) {
        return PerfilDTO.builder()
            .id(p.getId())
            .nombreVisible(p.getNombreVisible())
            .biografia(p.getBiografia())
            .alturaCm(p.getAlturaCm())
            .usuarioId(p.getUsuarioId())
            .comunaId(p.getComunaId())
            .ocupacion(p.getOcupacion())
            .build();
    }
}
