package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

}
