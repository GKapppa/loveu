package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer>{
    List<Mensaje> findByMatchId(Integer matchId);

    // Para cargar el chat en orden y revisar mensajes pendientes.
    List<Mensaje> findByMatchIdOrderBySentAtAsc(Integer matchId);
    List<Mensaje> findByPerfilReceptorIdAndReadFalse(Integer perfilReceptorId);
}
