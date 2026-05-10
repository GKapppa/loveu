package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    @Query("SELECT n FROM Notificacion n WHERE n.perfilDestinatario.id = ?1 AND n.read = false")
    List<Notificacion> findByPerfilDestinatarioIdAndReadFalse(Integer perfilDestinatarioId);

    @Query("SELECT n FROM Notificacion n WHERE n.perfilDestinatario.id = ?1 ORDER BY n.createdAt DESC")
    List<Notificacion> findByPerfilDestinatarioIdOrderByCreatedAtDesc(Integer perfilDestinatarioId);

    @Query("SELECT n FROM Notificacion n WHERE n.perfilDestinatario.id = ?1 AND n.read = true")
    List<Notificacion> findByPerfilDestinatarioIdAndReadTrue(Integer perfilDestinatarioId);
}
