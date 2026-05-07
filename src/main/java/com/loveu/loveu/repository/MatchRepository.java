package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findByPerfilAIdOrPerfilBId(Integer perfilAId, Integer perfilBid);
}
