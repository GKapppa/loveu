package com.usuario.usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usuario.usuarios.dto.PerfilDTO;
import com.usuario.usuarios.model.Perfil;
import com.usuario.usuarios.repository.PerfilRepository;

@ExtendWith(MockitoExtension.class)
class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private UsuarioValidaciones validaciones;

    @InjectMocks
    private PerfilService perfilService;

    @Test
    void getTodos_debeRetornarListaVaciaCuandoNoHayPerfiles() {
        when(perfilRepository.findByActivoTrue()).thenReturn(List.of());

        List<PerfilDTO> resultado = perfilService.getTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(perfilRepository).findByActivoTrue();
    }

    @Test
    void desactivarPerfil_debeMarcarComoInactivo() {
        Perfil perfil = new Perfil();
        perfil.setId(1);
        perfil.setActivo(true);

        when(validaciones.validarPerfilExiste(1)).thenReturn(perfil);
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);

        perfilService.desactivarPerfil(1);

        assertFalse(perfil.isActivo());
        verify(validaciones).validarPerfilExiste(1);
        verify(perfilRepository).save(perfil);
    }
}
