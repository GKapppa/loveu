package com.loveu.loveu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.FotoPerfil;

@Repository
public interface FotoPerfilRepository extends JpaRepository<FotoPerfil, Integer> {

    @Query("SELECT f FROM FotoPerfil f WHERE f.perfil.id = ?1")
    List<FotoPerfil> findByPerfilId(Integer perfilId);

    @Query("SELECT f FROM FotoPerfil f WHERE f.perfil.id = ?1 AND f.principal = true")
    Optional<FotoPerfil> findByPerfilIdAndPrincipalTrue(Integer perfilId);

    // Ordena las fotos como se mostrarian en el perfil.
    @Query("SELECT f FROM FotoPerfil f WHERE f.perfil.id = ?1 AND f.activo = true ORDER BY f.orden ASC")
    List<FotoPerfil> findByPerfilIdAndActivoTrueOrderByOrdenAsc(Integer perfilId);

    // Busca la foto principal visible del perfil.
    @Query("SELECT f FROM FotoPerfil f WHERE f.perfil.id = ?1 AND f.principal = true AND f.activo = true")
    Optional<FotoPerfil> findByPerfilIdAndPrincipalTrueAndActivoTrue(Integer perfilId);
}
