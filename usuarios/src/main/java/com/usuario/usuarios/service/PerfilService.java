package com.usuario.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.usuarios.dto.PerfilDTO;
import com.usuario.usuarios.model.Comuna;
import com.usuario.usuarios.model.Perfil;
import com.usuario.usuarios.model.Usuario;
import com.usuario.usuarios.repository.ComunaRepository;
import com.usuario.usuarios.repository.PerfilRepository;
import com.usuario.usuarios.repository.UsuarioRepository;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    public PerfilDTO crearPerfil(PerfilDTO dto){
        Optional<Perfil> perfilExistente = perfilRepository.findByUsuarioId(dto.getUsuarioId());
        if(perfilExistente.isPresent()){
            throw new RuntimeException("Este usuario ya tiene un perfil creado!");
        }
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        Comuna comuna = comunaRepository.findById(dto.getComunaId()).orElseThrow(() -> new RuntimeException("Comuna no encontrada!"));
        return null;
    }
}
