package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer>{

}
