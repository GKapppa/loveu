package com.interaccion.interaccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interaccion.interaccion.model.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

    List<Mensaje> findByMatchIdOrderBySentAtAsc(Integer matchId);

    List<Mensaje> findByPerfilReceptorIdAndLeidoFalse(Integer perfilReceptorId);
}
