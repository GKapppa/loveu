package com.usuario.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.usuario.usuarios.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    // Query escrita a mano: busca el perfil que pertenece a un usuario.
    @Query("SELECT p FROM Perfil p WHERE p.usuario.id = ?1")
    Optional<Perfil> findByUsuarioId(Integer userId);

    @Query("SELECT p FROM Perfil p WHERE p.comuna.id = ?1")
    List<Perfil> findByComunaId(Integer comunaId);

    // Solo trae perfiles activos, util para no mostrar perfiles dados de baja.
    @Query("SELECT p FROM Perfil p WHERE p.activo = true")
    List<Perfil> findByActivoTrue();
}
