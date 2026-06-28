package com.interaccion.interaccion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interaccion.interaccion.dto.MensajeDTO;
import com.interaccion.interaccion.model.Mensaje;
import com.interaccion.interaccion.repository.MensajeRepository;

@ExtendWith(MockitoExtension.class)
class MensajeServiceTest {

    @Mock
    private MensajeRepository mensajeRepository;

    @Mock
    private InteraccionValidaciones validaciones;

    @InjectMocks
    private MensajeService mensajeService;

    @Test
    void getPorMatch_debeRetornarListaVacia() {
        when(mensajeRepository.findByMatchIdOrderBySentAtAsc(1)).thenReturn(List.of());

        List<MensajeDTO> resultado = mensajeService.getPorMatch(1);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void getNoLeidos_debeRetornarListaVacia() {
        when(mensajeRepository.findByPerfilReceptorIdAndLeidoFalse(1)).thenReturn(List.of());

        List<MensajeDTO> resultado = mensajeService.getNoLeidos(1);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void marcarComoLeido_debeCambiarEstado() {
        Mensaje mensaje = new Mensaje();
        mensaje.setId(5);
        mensaje.setLeido(false);

        when(validaciones.validarMensajeExiste(5)).thenReturn(mensaje);
        when(mensajeRepository.save(any(Mensaje.class))).thenReturn(mensaje);

        mensajeService.marcarComoLeido(5);

        assertTrue(mensaje.isLeido());
        verify(mensajeRepository).save(mensaje);
    }

    @Test
    void enviarMensaje_debeGuardarYRetornarDTO() {
        doNothing().when(validaciones).validarNoSelfMensaje(1, 2);
        when(mensajeRepository.save(any(Mensaje.class))).thenAnswer(inv -> {
            Mensaje m = inv.getArgument(0);
            m.setId(77);
            return m;
        });

        MensajeDTO resultado = mensajeService.enviarMensaje(10, 1, 2, "Hola!");

        assertNotNull(resultado);
        assertEquals(77, resultado.getId());
        assertEquals("Hola!", resultado.getContenido());
    }
}
