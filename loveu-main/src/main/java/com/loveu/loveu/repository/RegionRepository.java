package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Region;

@Repository
public interface RegionRepository  extends JpaRepository<Region, Integer> {

}
