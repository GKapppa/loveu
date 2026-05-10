package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.DecisionSwipe;
import com.loveu.loveu.model.Swipe;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Integer> {

    boolean existsByPerfilOrigenIdAndPerfilDestinoId(Integer origenId, Integer destinoId);
    List<Swipe> findByPerfilOrigenId(Integer perfilOrigenId);
    List<Swipe> findByPerfilDestinoId(Integer perfilDestinoId);

    // Para revisar likes, dislikes o skips de un perfil.
    List<Swipe> findByPerfilOrigenIdAndDecision(Integer perfilOrigenId, DecisionSwipe decision);
    List<Swipe> findByPerfilDestinoIdAndDecision(Integer perfilDestinoId, DecisionSwipe decision);
    List<Swipe> findByPerfilOrigenIdAndActivoTrue(Integer perfilOrigenId);
}
