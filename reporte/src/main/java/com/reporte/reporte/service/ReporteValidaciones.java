package com.reporte.reporte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reporte.reporte.model.Reporte;
import com.reporte.reporte.repository.ReporteRepository;

@Service
public class ReporteValidaciones {

    @Autowired
    private ReporteRepository reporteRepository;

    public Reporte validarReporteExiste(Integer id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado: " + id));
    }

    public void validarNoSelfReporte(Integer reportante, Integer reportado) {
        if (reportante.equals(reportado)) {
            throw new RuntimeException("No puedes reportarte a ti mismo");
        }
    }

    public void validarNoDuplicateReporte(Integer reportante, Integer reportado) {
        boolean yaReportado = reporteRepository.findByPerfilReportanteAndActivoTrue(reportante)
                .stream()
                .anyMatch(r -> r.getPerfilReportado().equals(reportado));
        if (yaReportado) {
            throw new RuntimeException("Ya has reportado a este perfil anteriormente");
        }
    }
}
