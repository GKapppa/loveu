package com.reporte.reporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reporte.reporte.model.EstadoReporte;
import com.reporte.reporte.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByEstadoReporte(EstadoReporte estadoReporte);

    List<Reporte> findByPerfilReportadoId(Integer perfilReportadoId);

    List<Reporte> findByPerfilReportanteIdAndActivoTrue(Integer perfilReportanteId);
}
