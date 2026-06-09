package com.usuario.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usuario.usuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
