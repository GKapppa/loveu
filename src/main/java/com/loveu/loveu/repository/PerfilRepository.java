package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Perfil;

@Repository
public interface PerfilRepository  extends JpaRepository<Perfil, Integer> {

}
