package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Integer, Auth> {

}
