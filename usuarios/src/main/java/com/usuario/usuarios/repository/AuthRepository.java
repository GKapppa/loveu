package com.usuario.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.usuario.usuarios.model.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

    @Query("SELECT a FROM Auth a WHERE a.email = ?1")
    Optional<Auth> findByEmail(String email);

    boolean existsByEmail(String email);
}
