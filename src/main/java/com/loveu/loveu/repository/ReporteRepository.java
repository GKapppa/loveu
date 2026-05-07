package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

}
