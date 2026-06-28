package com.usuario.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.usuarios.model.Auth;
import com.usuario.usuarios.model.Comuna;
import com.usuario.usuarios.model.Perfil;
import com.usuario.usuarios.model.Preferencia;
import com.usuario.usuarios.model.Region;
import com.usuario.usuarios.model.Usuario;
import com.usuario.usuarios.repository.AuthRepository;
import com.usuario.usuarios.repository.ComunaRepository;
import com.usuario.usuarios.repository.PerfilRepository;
import com.usuario.usuarios.repository.PreferenciaRepository;
import com.usuario.usuarios.repository.RegionRepository;
import com.usuario.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioValidaciones {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    public Usuario validarUsuarioExiste(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public Auth validarAuthExiste(Integer id) {
        return authRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auth no encontrado con id: " + id));
    }

    public Auth validarAuthExistePorEmail(String email) {
        return authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Auth no encontrado con email: " + email));
    }

    public void validarEmailNoDuplicado(String email) {
        if (authRepository.existsByEmail(email)) {
            throw new RuntimeException("Este email ya esta registrado");
        }
    }

    public void validarCredenciales(Auth auth, String password) {
        if (!auth.isActivo() || !auth.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales incorrectas");
        }
    }

    public void validarUnPerfilPorUsuario(Integer usuarioId) {
        if (perfilRepository.findByUsuarioId(usuarioId).isPresent()) {
            throw new RuntimeException("Este usuario ya tiene un perfil creado");
        }
    }

    public Perfil validarPerfilExiste(Integer id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado: " + id));
    }

    public Perfil validarPerfilExistePorUsuario(Integer usuarioId) {
        return perfilRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado para usuario: " + usuarioId));
    }

    public Comuna validarComunaExiste(Integer id) {
        return comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada: " + id));
    }

    public Region validarRegionExiste(Integer id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region no encontrada: " + id));
    }

    public Preferencia validarPreferenciaExiste(Integer id) {
        return preferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada con id: " + id));
    }

    public void validarUnicaPreferenciaPorPerfil(Integer perfilId) {
        if (preferenciaRepository.existsByPerfilIdAndActivoTrue(perfilId)) {
            throw new RuntimeException("Este perfil ya tiene una preferencia creada");
        }
    }
}
