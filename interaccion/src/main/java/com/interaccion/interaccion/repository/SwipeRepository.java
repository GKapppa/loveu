package com.interaccion.interaccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interaccion.interaccion.model.Swipe;

public interface SwipeRepository extends JpaRepository<Swipe, Integer> {

    List<Swipe> findByPerfilOrigenId(Integer perfilOrigenId);

    List<Swipe> findByPerfilDestinoId(Integer perfilDestinoId);

    boolean existsByPerfilOrigenIdAndPerfilDestinoId(Integer perfilOrigenId, Integer perfilDestinoId);
}
