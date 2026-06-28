package com.usuario.usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usuario.usuarios.dto.ComunaDTO;
import com.usuario.usuarios.model.Comuna;
import com.usuario.usuarios.repository.ComunaRepository;

@ExtendWith(MockitoExtension.class)
class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private UsuarioValidaciones validaciones;

    @InjectMocks
    private ComunaService comunaService;

    @Test
    void listarTodo_debeRetornarListaVaciaCuandoNoHayComunas() {
        when(comunaRepository.findAll()).thenReturn(List.of());

        List<ComunaDTO> resultado = comunaService.listarTodo();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(comunaRepository).findAll();
    }

    @Test
    void buscarPorId_debeLanzarExcepcionSiNoExiste() {
        when(validaciones.validarComunaExiste(99))
                .thenThrow(new RuntimeException("Comuna no encontrada: 99"));

        assertThrows(RuntimeException.class, () -> comunaService.buscarPorID(99));
    }
}
