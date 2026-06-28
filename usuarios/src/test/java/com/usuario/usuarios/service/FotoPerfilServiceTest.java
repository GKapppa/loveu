package com.usuario.usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usuario.usuarios.dto.FotoPerfilDTO;
import com.usuario.usuarios.model.FotoPerfil;
import com.usuario.usuarios.model.Perfil;
import com.usuario.usuarios.repository.FotoPerfilRepository;
import com.usuario.usuarios.repository.PerfilRepository;

@ExtendWith(MockitoExtension.class)
class FotoPerfilServiceTest {

    @Mock
    private FotoPerfilRepository fotoPerfilRepository;

    @Mock
    private PerfilRepository perfilRepository;

    @InjectMocks
    private FotoPerfilService fotoPerfilService;

    @Test
    void listarPorPerfil_debeRetornarListaVacia() {
        when(fotoPerfilRepository.findByPerfilIdAndActivoTrueOrderByOrdenAsc(1)).thenReturn(List.of());

        List<FotoPerfilDTO> resultado = fotoPerfilService.listarPorPerfil(1);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void desactivarFoto_debeMarcarComoInactiva() {
        FotoPerfil foto = new FotoPerfil();
        foto.setId(1);
        foto.setActivo(true);

        when(fotoPerfilRepository.findById(1)).thenReturn(Optional.of(foto));
        when(fotoPerfilRepository.save(any(FotoPerfil.class))).thenReturn(foto);

        fotoPerfilService.desactivarFoto(1);

        assertFalse(foto.isActivo());
        verify(fotoPerfilRepository).save(foto);
    }

    @Test
    void crearFotoPerfil_debeGuardarYRetornarDTO() {
        Perfil perfil = new Perfil();
        perfil.setId(10);

        FotoPerfilDTO dto = FotoPerfilDTO.builder()
                .perfilId(10)
                .urlFoto("http://foto.jpg")
                .orden(1)
                .build();

        when(perfilRepository.findById(10)).thenReturn(Optional.of(perfil));
        when(fotoPerfilRepository.save(any(FotoPerfil.class))).thenAnswer(inv -> {
            FotoPerfil f = inv.getArgument(0);
            f.setId(100);
            return f;
        });

        FotoPerfilDTO resultado = fotoPerfilService.crearFotoPerfil(dto);

        assertNotNull(resultado);
        assertEquals(100, resultado.getFotoId());
    }
}
