package com.interaccion.interaccion.repository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query("SELECT m FROM Match m WHERE m.perfilA.id = ?1 OR m.perfilB.id = ?2")
    List<Match> findByPerfilAIdOrPerfilBId(Integer perfilAId, Integer perfilBid);

    @Query("SELECT m FROM Match m WHERE m.status = ?1")
    List<Match> findByStatus(MatchStatus status);
}

