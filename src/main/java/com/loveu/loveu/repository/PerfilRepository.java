package com.loveu.loveu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    @Query("SELECT p FROM Perfil p WHERE p.usuario.id = ?1")
    Optional<Perfil> findByUsuarioId(Integer userId);

    @Query("SELECT p FROM Perfil p WHERE p.comuna.id = ?1")
    List<Perfil> findByComunaId(Integer comunaId);

    @Query("SELECT p FROM Perfil p WHERE p.activo = true")
    List<Perfil> findByActivoTrue();
}
