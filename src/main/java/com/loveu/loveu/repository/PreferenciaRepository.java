package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Preferencia;

@Repository
public interface PreferenciaRepository  extends JpaRepository<Preferencia, Integer> {

}
