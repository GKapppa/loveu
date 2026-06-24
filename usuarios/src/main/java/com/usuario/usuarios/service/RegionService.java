package com.usuario.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.usuarios.dto.RegionDTO;
import com.usuario.usuarios.model.Region;
import com.usuario.usuarios.repository.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private UsuarioValidaciones validaciones;

    private RegionDTO toDTO(Region region) {
        return RegionDTO.builder()
                .id(region.getId())
                .nombreRegion(region.getNombreRegion())
                .abreviatura(region.getAbreviatura())
                .build();
    }

    public List<RegionDTO> listarTodo() {
        return regionRepository.findAll().stream().map(this::toDTO).toList();
    }

    public RegionDTO buscarPorId(Integer id) {
        Region region = validaciones.validarRegionExiste(id);
        return toDTO(region);
    }
}
