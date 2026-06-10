package com.usuario.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.usuarios.dto.ComunaDTO;
import com.usuario.usuarios.model.Comuna;
import com.usuario.usuarios.repository.ComunaRepository;

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
