package com.usuario.usuarios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.usuarios.dto.PerfilDTO;
import com.usuario.usuarios.model.Perfil;
import com.usuario.usuarios.repository.PerfilRepository;

@Service
public class PerfilService {

    private static final Logger log = LoggerFactory.getLogger(PerfilService.class);

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioValidaciones validaciones;

    public PerfilDTO crearPerfil(Integer usuarioId, Integer comunaId) {
        log.info("[v2] Creando perfil para usuarioId={}", usuarioId);
        validaciones.validarUnPerfilPorUsuario(usuarioId);
        validaciones.validarUsuarioExiste(usuarioId);
        validaciones.validarComunaExiste(comunaId);

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
        Perfil perfil = validaciones.validarPerfilExistePorUsuario(usuarioId);
        return toDTO(perfil);
    }

    public PerfilDTO actualizarPerfil(Integer id, PerfilDTO dto) {
        log.info("[v2] Actualizando perfil id={}", id);
        Perfil perfil = validaciones.validarPerfilExiste(id);

        if (dto.getNombreVisible() != null) perfil.setNombreVisible(dto.getNombreVisible());
        if (dto.getBiografia() != null) perfil.setBiografia(dto.getBiografia());
        if (dto.getOcupacion() != null) perfil.setOcupacion(dto.getOcupacion());
        if (dto.getAlturaCm() != null) perfil.setAlturaCm(dto.getAlturaCm());

        perfil = perfilRepository.save(perfil);
        return toDTO(perfil);
    }

    public void desactivarPerfil(Integer id) {
        log.info("[v2] Desactivando perfil id={}", id);
        Perfil perfil = validaciones.validarPerfilExiste(id);
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
