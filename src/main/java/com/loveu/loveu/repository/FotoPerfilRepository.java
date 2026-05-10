package com.loveu.loveu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.FotoPerfil;

@Repository
public interface FotoPerfilRepository extends JpaRepository<FotoPerfil, Integer> {
    List<FotoPerfil> findByPerfilId(Integer perfilId);
    Optional<FotoPerfil> findByPerfilIdAndPrincipalTrue(Integer perfilId);

    // Fotos que realmente se mostrarian en el perfil.
    List<FotoPerfil> findByPerfilIdAndActivoTrueOrderByOrdenAsc(Integer perfilId);
    Optional<FotoPerfil> findByPerfilIdAndPrincipalTrueAndActivoTrue(Integer perfilId);
}
