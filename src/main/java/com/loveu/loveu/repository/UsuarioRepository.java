package com.loveu.loveu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loveu.loveu.model.Usuario;
import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByActivo(Boolean activo);

    // Busqueda simple por datos basicos del usuario.
    List<Usuario> findByPrimerNombreContainingIgnoreCase(String primerNombre);
    List<Usuario> findByPrimerApellidoContainingIgnoreCase(String primerApellido);
}   
