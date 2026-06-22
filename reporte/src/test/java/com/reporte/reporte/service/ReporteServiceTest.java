package com.reporte.reporte.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reporte.reporte.dto.ReporteDTO;
import com.reporte.reporte.model.Reporte;
import com.reporte.reporte.repository.ReporteRepository;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void obtenerTodos_debeRetornarListaVaciaCuandoNoHayReportes(){
        when(reporteRepository.findAll()).thenReturn(List.of());

        List<ReporteDTO> resultado = reporteService.getTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(reporteRepository).findAll();
    }

    @Test
    void crearReporte_debeGuardarYRetornarDTO(){
        Reporte guardado = new Reporte();
        guardado.setRazonReporte("Reporte por weko");
        when(reporteRepository.save(any())).thenReturn(guardado);

        ReporteDTO resultado = reporteService.crearReporte(2, 1, "Reporte por weko");
        assertNotNull(resultado);

    }

    @Test
    void eliminarReporte_DebeMarcarComoEliminado(){
        reporteService.eliminarReporte(1);
        verify(reporteRepository).deleteById(1);
    }

}
