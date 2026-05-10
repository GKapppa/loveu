package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Match;
import com.loveu.loveu.model.MatchStatus;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findByPerfilAIdOrPerfilBId(Integer perfilAId, Integer perfilBid);

    // Consultas simples para separar matches activos de deshechos.
    List<Match> findByStatus(MatchStatus status);
    List<Match> findByPerfilAIdAndStatus(Integer perfilAId, MatchStatus status);
    List<Match> findByPerfilBIdAndStatus(Integer perfilBId, MatchStatus status);
}
