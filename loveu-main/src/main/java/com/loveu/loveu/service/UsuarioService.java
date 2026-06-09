package com.loveu.loveu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.UsuarioDTO;
import com.loveu.loveu.model.Usuario;
import com.loveu.loveu.repository.UsuarioRepository;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    // Convertimos la entidad JPA A DTO
    private UsuarioDTO toDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .usuarioId(usuario.getId())
                .primerNombre(usuario.getPrimerNombre())
                .primerApellido(usuario.getPrimerApellido())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .telefono(usuario.getTelefono())
                .build();
    }

    public List<UsuarioDTO> obtenerTodos(){
        return usuarioRepository.findAll().stream().map(this::toDTO).toList();
    }

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();

        usuario.setPrimerNombre(usuarioDTO.getPrimerNombre());
        usuario.setPrimerApellido(usuarioDTO.getPrimerApellido());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return toDTO(usuarioGuardado);
    }

    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id:" + id));

        usuario.setPrimerNombre(usuarioDTO.getPrimerNombre());
        usuario.setPrimerApellido(usuarioDTO.getPrimerApellido());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setTelefono(usuarioDTO.getTelefono());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return toDTO(usuarioActualizado);
    }

    public void eliminarUsuario(Integer id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id:" + id));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);

    }
}