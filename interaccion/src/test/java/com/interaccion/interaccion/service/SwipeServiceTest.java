package com.interaccion.interaccion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interaccion.interaccion.dto.SwipeDTO;
import com.interaccion.interaccion.model.Swipe;
import com.interaccion.interaccion.repository.SwipeRepository;

@ExtendWith(MockitoExtension.class)
class SwipeServiceTest {

    @Mock
    private SwipeRepository swipeRepository;

    @Mock
    private InteraccionValidaciones validaciones;

    @InjectMocks
    private SwipeService swipeService;

    @Test
    void getTodos_debeRetornarListaVacia() {
        when(swipeRepository.findAll()).thenReturn(List.of());

        List<SwipeDTO> resultado = swipeService.getTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void getPorOrigen_debeRetornarListaVacia() {
        when(swipeRepository.findByPerfilOrigenId(1)).thenReturn(List.of());

        List<SwipeDTO> resultado = swipeService.getPorOrigen(1);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void crearSwipe_debeLanzarExcepcionSiEsSelfSwipe() {
        doThrow(new RuntimeException("No puedes swipearte a ti mismo"))
                .when(validaciones).validarNoSelfSwipe(1, 1);

        assertThrows(RuntimeException.class, () -> swipeService.crearSwipe(1, 1, "LIKE"));
    }
}
