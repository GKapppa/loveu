package com.loveu.loveu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

    // Más QUERY DE JPA

    Optional<Auth> findByEmail(String email);
    boolean existsByEmail(String email);
}
