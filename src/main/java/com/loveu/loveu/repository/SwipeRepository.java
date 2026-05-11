package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.DecisionSwipe;
import com.loveu.loveu.model.Swipe;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Integer> {

    boolean existsByPerfilOrigenIdAndPerfilDestinoId(Integer origenId, Integer destinoId);

    @Query("SELECT s FROM Swipe s WHERE s.perfilOrigen.id = ?1")
    List<Swipe> findByPerfilOrigenId(Integer perfilOrigenId);

    @Query("SELECT s FROM Swipe s WHERE s.perfilDestino.id = ?1")
    List<Swipe> findByPerfilDestinoId(Integer perfilDestinoId);

    @Query("SELECT s FROM Swipe s WHERE s.decision = ?1")
    List<Swipe> buscarPorDecision(DecisionSwipe decision);

    @Query("SELECT s FROM Swipe s WHERE s.activo = true")
    List<Swipe> buscarActivos();

    List<Swipe> findByPerfilOrigenIdAndDecision(Integer perfilOrigenId, DecisionSwipe decision);
    List<Swipe> findByPerfilDestinoIdAndDecision(Integer perfilDestinoId, DecisionSwipe decision);
    List<Swipe> findByPerfilOrigenIdAndActivoTrue(Integer perfilOrigenId);
}
