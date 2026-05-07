package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
    List<Comuna> findByRegionId(Integer regionId);
}
