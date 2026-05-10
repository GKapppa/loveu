package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Match;
import com.loveu.loveu.model.MatchStatus;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query("SELECT m FROM Match m WHERE m.perfilA.id = ?1 OR m.perfilB.id = ?2")
    List<Match> findByPerfilAIdOrPerfilBId(Integer perfilAId, Integer perfilBid);

    @Query("SELECT m FROM Match m WHERE m.status = ?1")
    List<Match> findByStatus(MatchStatus status);

    @Query("SELECT m FROM Match m WHERE m.perfilA.id = ?1 AND m.status = ?2")
    List<Match> findByPerfilAIdAndStatus(Integer perfilAId, MatchStatus status);

    @Query("SELECT m FROM Match m WHERE m.perfilB.id = ?1 AND m.status = ?2")
    List<Match> findByPerfilBIdAndStatus(Integer perfilBId, MatchStatus status);
}
