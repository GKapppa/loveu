package com.loveu.loveu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.RegionDTO;
import com.loveu.loveu.model.Region;
import com.loveu.loveu.repository.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    private RegionDTO toDTO(Region region){
        return RegionDTO.builder()
                .nombreRegion(region.getNombreRegion())
                .abreviatura(region.getAbreviatura())
                .build();
    }

    public List<RegionDTO> listarTodos(){
        return regionRepository.findAll().stream().map(this::toDTO).toList();
    }

    public RegionDTO buscarPorId(Integer id){
        Region region =  regionRepository.findById(id).orElseThrow(() -> new RuntimeException("Region no encontrada!"));

        return toDTO(region);
    }
}
