package com.usuario.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.usuarios.model.Preferencia;

@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Integer> {

    Optional<Preferencia> findByPerfilIdAndActivoTrue(Integer perfilId);

    boolean existsByPerfilIdAndActivoTrue(Integer perfilId);
}
