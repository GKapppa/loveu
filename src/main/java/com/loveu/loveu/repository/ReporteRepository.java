package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.EstadoReporte;
import com.loveu.loveu.model.Reporte;
import java.util.List;


@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
    List<Reporte> findByEstadoReporte(EstadoReporte estadoReporte);
    List<Reporte> findByPerfilReportadoId(Integer perfilId);

    // Para revisar historial de reportes en moderacion.
    List<Reporte> findByPerfilReportanteId(Integer perfilId);
    List<Reporte> findByPerfilReportadoIdAndActivoTrue(Integer perfilId);
}
