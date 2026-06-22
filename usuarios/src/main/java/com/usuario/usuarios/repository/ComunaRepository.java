package com.usuario.usuarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.usuario.usuarios.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {

    @Query("SELECT c FROM Comuna c WHERE c.regionid = ?1")
    List<Comuna> findByRegionId(Integer regionId);

    List<Comuna> findByNombreComunaContainingIgnoreCase(String nombreComuna);
}
