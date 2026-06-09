package com.loveu.loveu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.activo = ?1")
    List<Usuario> findByActivo(Boolean activo);

    List<Usuario> findByPrimerNombreContainingIgnoreCase(String primerNombre);

    List<Usuario> findByPrimerApellidoContainingIgnoreCase(String primerApellido);
}
