package com.reporte.reporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reporte.reporte.model.EstadoReporte;
import com.reporte.reporte.model.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByEstadoReporte(EstadoReporte estadoReporte);

    List<Reporte> findByPerfilReportadoId(Integer perfilReportadoId);

    List<Reporte> findByPerfilReportanteIdAndActivoTrue(Integer perfilReportanteId);
}
