package com.interaccion.interaccion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.interaccion.interaccion.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    @Query("SELECT n FROM Notificacion n WHERE n.perfilDestinatarioId = ?1 AND n.leido = false")
    List<Notificacion> findByPerfilDestinatarioIdAndLeidoFalse(Integer perfilDestinatarioId);

    @Query("SELECT n FROM Notificacion n WHERE n.perfilDestinatarioId = ?1 ORDER BY n.createdAt DESC")
    List<Notificacion> findByPerfilDestinatarioIdOrderByCreatedAtDesc(Integer perfilDestinatarioId);

    @Query("SELECT n FROM Notificacion n WHERE n.perfilDestinatarioId = ?1 AND n.leido = true")
    List<Notificacion> findByPerfilDestinatarioIdAndLeidoTrue(Integer perfilDestinatarioId);
}
