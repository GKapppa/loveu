package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByUserIdAndReadFalse(Integer userId);
}
