package com.loveu.loveu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.FotoPerfilDTO;
import com.loveu.loveu.model.FotoPerfil;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.repository.FotoPerfilRepository;
import com.loveu.loveu.repository.PerfilRepository;

@Service
public class FotoPerfilService {

    @Autowired
    private FotoPerfilRepository fotoPerfilRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    // Se devuelve fotoId porque sirve para marcar principal o desactivar.
    private FotoPerfilDTO toDTO(FotoPerfil fotoPerfil){
        return FotoPerfilDTO.builder()
                .fotoId(fotoPerfil.getId())
                .perfilId(fotoPerfil.getPerfil().getId())
                .urlFoto(fotoPerfil.getUrlFoto())
                .principal(fotoPerfil.isPrincipal())
                .orden(fotoPerfil.getOrden())
                .fechaSubida(fotoPerfil.getFechaSubida())
                .build();
    }

    public FotoPerfilDTO crearFotoPerfil(FotoPerfilDTO fotoPerfilDTO){
        Perfil perfil = perfilRepository.findById(fotoPerfilDTO.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado! por id:" + fotoPerfilDTO.getPerfilId()));

        FotoPerfil fotoPerfil = new FotoPerfil();

        fotoPerfil.setPerfil(perfil);
        fotoPerfil.setUrlFoto(fotoPerfilDTO.getUrlFoto());
        fotoPerfil.setPrincipal(fotoPerfilDTO.isPrincipal());
        fotoPerfil.setOrden(fotoPerfilDTO.getOrden());
        fotoPerfil.setFechaSubida(fotoPerfilDTO.getFechaSubida());
        fotoPerfil.setActivo(true);

        FotoPerfil fotoPerfilGuardada = fotoPerfilRepository.save(fotoPerfil);
        return toDTO(fotoPerfilGuardada);
    }

    public List<FotoPerfilDTO> listarPorPerfil(Integer perfilId){
        return fotoPerfilRepository.findByPerfilIdAndActivoTrueOrderByOrdenAsc(perfilId).stream().map(this::toDTO).toList();
    }

    public FotoPerfilDTO buscarPrincipal(Integer perfilId){
        FotoPerfil fotoPerfil = fotoPerfilRepository.findByPerfilIdAndPrincipalTrueAndActivoTrue(perfilId).orElseThrow(() -> new RuntimeException("Foto principal no encontrada para perfil:" + perfilId));
        return toDTO(fotoPerfil);
    }

    public FotoPerfilDTO marcarComoPrincipal(Integer fotoId){
        FotoPerfil fotoPerfil = fotoPerfilRepository.findById(fotoId).orElseThrow(() -> new RuntimeException("Foto no encontrada con id:" + fotoId));
        List<FotoPerfil> fotosPerfil = fotoPerfilRepository.findByPerfilIdAndActivoTrueOrderByOrdenAsc(fotoPerfil.getPerfil().getId());

        // Primero quitamos la marca principal de todas las fotos del perfil.
        for (FotoPerfil foto : fotosPerfil) {
            foto.setPrincipal(false);
        }

        fotoPerfil.setPrincipal(true);
        fotoPerfilRepository.saveAll(fotosPerfil);

        FotoPerfil fotoActualizada = fotoPerfilRepository.save(fotoPerfil);
        return toDTO(fotoActualizada);
    }

    public void desactivarFoto(Integer fotoId){
        FotoPerfil fotoPerfil = fotoPerfilRepository.findById(fotoId).orElseThrow(() -> new RuntimeException("Foto no encontrada con id:" + fotoId));
        fotoPerfil.setActivo(false);
        fotoPerfilRepository.save(fotoPerfil);
    }

}
