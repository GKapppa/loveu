package com.usuario.usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usuario.usuarios.dto.RegionDTO;
import com.usuario.usuarios.model.Region;
import com.usuario.usuarios.repository.RegionRepository;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private UsuarioValidaciones validaciones;

    @InjectMocks
    private RegionService regionService;

    @Test
    void listarTodo_debeRetornarListaVaciaCuandoNoHayRegiones() {
        when(regionRepository.findAll()).thenReturn(List.of());

        List<RegionDTO> resultado = regionService.listarTodo();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(regionRepository).findAll();
    }

    @Test
    void buscarPorId_debeRetornarDTO() {
        Region region = new Region();
        region.setId(1);
        region.setNombreRegion("Metropolitana");
        region.setAbreviatura("RM");

        when(validaciones.validarRegionExiste(1)).thenReturn(region);

        RegionDTO resultado = regionService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Metropolitana", resultado.getNombreRegion());
        verify(validaciones).validarRegionExiste(1);
    }
}
