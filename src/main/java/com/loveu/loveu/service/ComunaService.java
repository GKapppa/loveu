package com.loveu.loveu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.ComunaDTO;
import com.loveu.loveu.model.Comuna;
import com.loveu.loveu.repository.ComunaRepository;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    private ComunaDTO toDTO(Comuna comuna){
        return ComunaDTO.builder()
                .nombreComuna(comuna.getNombreComuna())
                .build();
    }

    public List<ComunaDTO> listarTodo(){
        return comunaRepository.findAll().stream().map(this::toDTO).toList();
    }

    public ComunaDTO buscarPorID(Integer id){
        Comuna comuna = comunaRepository.findById(id).orElseThrow(() -> new RuntimeException("Comuna no encontrada"));

        return toDTO(comuna);
    }
}
