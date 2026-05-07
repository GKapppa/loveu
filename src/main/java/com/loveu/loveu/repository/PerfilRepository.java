package com.loveu.loveu.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Perfil;

@Repository
public interface PerfilRepository  extends JpaRepository<Perfil, Integer> {
    // Lo usamos para evitar trabajar con algun null.
    Optional<Perfil> findByUserId(Integer userId); // Uso de Optional: Para manehar un posible "Puede que exista un resultado, o puede que no exista "
    List<Perfil> findByComunaId(Integer comunaId); // Para obtener perfiles por comuna.
}
