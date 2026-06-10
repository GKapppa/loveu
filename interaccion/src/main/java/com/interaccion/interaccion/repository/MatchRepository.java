package com.interaccion.interaccion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.interaccion.interaccion.model.Match;
import com.interaccion.interaccion.model.MatchStatus;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query("SELECT m FROM Match m WHERE m.perfilAId = ?1 OR m.perfilBId = ?2")
    List<Match> findByPerfilAIdOrPerfilBId(Integer perfilAId, Integer perfilBId);

    @Query("SELECT m FROM Match m WHERE m.status = ?1")
    List<Match> findByStatus(String status);
}
