package com.usuario.usuarios.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.usuario.usuarios.controller.UsuarioController;
import com.usuario.usuarios.dto.UsuarioDTO;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(UsuarioController.class).obtenerPorId(dto.getUsuarioId())).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).obtenerTodos()).withRel("usuarios"),
            linkTo(methodOn(UsuarioController.class).actualizarUsuario(dto.getUsuarioId(), null)).withRel("actualizar"),
            linkTo(methodOn(UsuarioController.class).eliminarUsuario(dto.getUsuarioId())).withRel("eliminar")
        );
    }
}
