package com.usuario.usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usuario.usuarios.dto.PreferenciaDTO;
import com.usuario.usuarios.model.Perfil;
import com.usuario.usuarios.model.Preferencia;
import com.usuario.usuarios.repository.PreferenciaRepository;

@ExtendWith(MockitoExtension.class)
class PreferenciaServiceTest {

    @Mock
    private PreferenciaRepository preferenciaRepository;

    @Mock
    private UsuarioValidaciones validaciones;

    @InjectMocks
    private PreferenciaService preferenciaService;

    @Test
    void listarTodas_debeRetornarListaVacia() {
        when(preferenciaRepository.findAll()).thenReturn(List.of());

        List<PreferenciaDTO> resultado = preferenciaService.listarTodas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void desactivarPreferencia_debeMarcarComoInactiva() {
        Preferencia pref = Preferencia.builder()
                .id(1)
                .perfilId(10)
                .edadMin(25)
                .edadMax(35)
                .distanciaMaxKm(50)
                .generoPreferido("Todos")
                .activo(true)
                .build();

        when(validaciones.validarPreferenciaExiste(1)).thenReturn(pref);
        when(preferenciaRepository.save(any(Preferencia.class))).thenReturn(pref);

        preferenciaService.desactivarPreferencia(1);

        assertFalse(pref.isActivo());
        verify(preferenciaRepository).save(pref);
    }

    @Test
    void buscarPorPerfil_debeLanzarExcepcionSiNoExistePerfil() {
        when(validaciones.validarPerfilExiste(99))
                .thenThrow(new RuntimeException("Perfil no encontrado: 99"));

        assertThrows(RuntimeException.class, () -> preferenciaService.buscarPorPerfil(99));
    }

    @Test
    void crearPreferencia_debeGuardarYRetornarDTO() {
        Perfil perfil = new Perfil();
        perfil.setId(10);

        PreferenciaDTO dto = PreferenciaDTO.builder()
                .perfilId(10)
                .edadMin(25)
                .edadMax(35)
                .distanciaMaxKm(30)
                .generoPreferido("Femenino")
                .build();

        when(validaciones.validarPerfilExiste(10)).thenReturn(perfil);
        doNothing().when(validaciones).validarUnicaPreferenciaPorPerfil(10);
        when(preferenciaRepository.save(any(Preferencia.class))).thenAnswer(inv -> {
            Preferencia p = inv.getArgument(0);
            p.setId(100);
            return p;
        });

        PreferenciaDTO resultado = preferenciaService.crearPreferencia(dto);

        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        assertEquals("Femenino", resultado.getGeneroPreferido());
    }
}
