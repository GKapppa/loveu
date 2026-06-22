package com.usuario.usuarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.usuarios.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    List<Region> findByNombreRegionContainingIgnoreCase(String nombreRegion);
}
