package com.usuario.usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usuario.usuarios.dto.UsuarioDTO;
import com.usuario.usuarios.model.Usuario;
import com.usuario.usuarios.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioValidaciones validaciones;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void obtenerTodos_debeRetornarListaVaciaCuandoNoHayUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<UsuarioDTO> resultado = usuarioService.obtenerTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).findAll();
    }

    @Test
    void crearUsuario_debeGuardarYRetornarDTO() {
        UsuarioDTO dto = UsuarioDTO.builder()
                .primerNombre("Anakin")
                .primerApellido("Skywalker")
                .build();

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1);
        usuarioGuardado.setPrimerNombre("Anakin");
        usuarioGuardado.setPrimerApellido("Skywalker");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        UsuarioDTO resultado = usuarioService.crearUsuario(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getUsuarioId());
        assertEquals("Anakin", resultado.getPrimerNombre());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void eliminarUsuario_debeMarcarComoInactivo() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setActivo(true);

        when(validaciones.validarUsuarioExiste(1)).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.eliminarUsuario(1);

        assertFalse(usuario.isActivo());
        verify(validaciones).validarUsuarioExiste(1);
        verify(usuarioRepository).save(usuario);
    }
}
