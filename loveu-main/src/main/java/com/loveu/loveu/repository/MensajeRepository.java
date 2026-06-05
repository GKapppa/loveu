package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer>{

    @Query("SELECT m FROM Mensaje m WHERE m.match.id = ?1")
    List<Mensaje> findByMatchId(Integer matchId);

    @Query("SELECT m FROM Mensaje m WHERE m.match.id = ?1 ORDER BY m.sentAt ASC")
    List<Mensaje> findByMatchIdOrderBySentAtAsc(Integer matchId);

    @Query("SELECT m FROM Mensaje m WHERE m.perfilReceptor.id = ?1 AND m.read = false")
    List<Mensaje> findByPerfilReceptorIdAndReadFalse(Integer perfilReceptorId);
}
