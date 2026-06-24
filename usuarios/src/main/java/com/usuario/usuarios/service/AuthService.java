package com.usuario.usuarios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.usuarios.dto.AuthDTO;
import com.usuario.usuarios.dto.AuthRequestDTO;
import com.usuario.usuarios.model.Auth;
import com.usuario.usuarios.model.Usuario;
import com.usuario.usuarios.repository.AuthRepository;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UsuarioValidaciones validaciones;

    private AuthDTO toDTO(Auth auth) {
        return AuthDTO.builder()
                .email(auth.getEmail())
                .rol(auth.getRol())
                .build();
    }

    public List<AuthDTO> listarTodos() {
        log.info("[v2] Listando todos los auth");
        return authRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AuthDTO crearAuth(Integer usuarioId, AuthRequestDTO authDTO) {
        log.info("[v2] Creando auth para usuarioId={}", usuarioId);
        validaciones.validarEmailNoDuplicado(authDTO.getEmail());

        Usuario usuario = validaciones.validarUsuarioExiste(usuarioId);

        Auth auth = Auth.builder()
                .email(authDTO.getEmail())
                .password(authDTO.getPassword())
                .rol(authDTO.getRol())
                .usuarioId(usuario.getId())
                .activo(true)
                .build();

        auth = authRepository.save(auth);
        return toDTO(auth);
    }

    public AuthDTO login(AuthRequestDTO authDTO) {
        log.info("[v2] Login con email={}", authDTO.getEmail());
        Auth auth = validaciones.validarAuthExistePorEmail(authDTO.getEmail());
        validaciones.validarCredenciales(auth, authDTO.getPassword());
        return toDTO(auth);
    }

    public AuthDTO buscarPorEmail(String email) {
        log.info("[v2] Buscando auth por email={}", email);
        Auth auth = validaciones.validarAuthExistePorEmail(email);
        return toDTO(auth);
    }

    public void desactivarAuth(Integer id) {
        log.info("[v2] Desactivando auth id={}", id);
        Auth auth = validaciones.validarAuthExiste(id);
        auth.setActivo(false);
        authRepository.save(auth);
    }
}
