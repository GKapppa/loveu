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

import com.interaccion.interaccion.dto.NotificacionDTO;
import com.interaccion.interaccion.model.Notificacion;
import com.interaccion.interaccion.repository.NotificacionRepository;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void getNoLeidas_debeRetornarListaVaciaCuandoNoHayNotificaciones() {
        when(notificacionRepository.findByPerfilDestinatarioIdAndLeidoFalse(1)).thenReturn(List.of());

        List<NotificacionDTO> resultado = notificacionService.getNoLeidas(1);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(notificacionRepository).findByPerfilDestinatarioIdAndLeidoFalse(1);
    }

    @Test
    void crearNotificacion_debeGuardarYRetornarDTO() {
        Notificacion notificacionGuardada = Notificacion.builder()
                .perfilDestinatarioId(1)
                .type("MATCH")
                .message("Tienes un nuevo Match")
                .build();
        notificacionGuardada.setId(123);

        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);

        NotificacionDTO resultado = notificacionService.crearNotificacion(1, "MATCH", "Tienes un nuevo Match!");

        assertNotNull(resultado);
        assertEquals(123, resultado.getId());
        assertEquals("Tienes un nuevo Match!", resultado.getMessage());
        verify(notificacionRepository).save(any(Notificacion.class));
    }

    @Test
    void marcarComoLeida_debeModificarEstado() {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(5);
        notificacion.setLeido(false);

        when(notificacionRepository.findById(5)).thenReturn(Optional.of(notificacion));
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);

        notificacionService.marcarComoLeida(5);

        assertTrue(notificacion.isLeido());
        verify(notificacionRepository).findById(5);
        verify(notificacionRepository).save(notificacion);
    }
}

