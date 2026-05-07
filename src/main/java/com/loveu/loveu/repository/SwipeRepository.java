package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Swipe;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Integer> {

    boolean existsByPerfilOrigenIdAndPerfilDestinoId(Integer origenId, Integer destinoId);
    List<Swipe> findByPerfilOrigenId(Integer perfilOrigenId);
    List<Swipe> findByPerfilDestinoId(Integer perfilDestinoId);
}
