package com.loveu.loveu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.AuthDTO;
import com.loveu.loveu.dto.AuthRequestDTO;
import com.loveu.loveu.model.Auth;
import com.loveu.loveu.model.Usuario;
import com.loveu.loveu.repository.AuthRepository;
import com.loveu.loveu.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private AuthDTO toDTO(Auth auth){
        return AuthDTO.builder()
                .email(auth.getEmail())
                .rol(auth.getRol())
                .build();
    }

    public List<AuthDTO> listarTodos(){
        return authRepository.findAll().stream().map(this::toDTO).toList();
    }

    public AuthDTO crearAuth(AuthRequestDTO authDTO){

        if (authRepository.existsByEmail(authDTO.getEmail())) {
            throw new RuntimeException("Este email ya esta registrado");
        }

        Usuario usuario = usuarioRepository.findById(authDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Auth auth = new Auth();

        auth.setEmail(authDTO.getEmail());
        auth.setPassword(authDTO.getPassword());
        auth.setRol(authDTO.getRol());
        auth.setActivo(true);
        auth.setUsuario(usuario);

        Auth authGuardado = authRepository.save(auth);
        return toDTO(authGuardado);
    }

    public AuthDTO login(AuthRequestDTO authDTO){
        Auth auth = authRepository.findByEmail(authDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!auth.isActivo() || !auth.getPassword().equals(authDTO.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        return toDTO(auth);
    }

    public AuthDTO buscarPorEmail(String email){
        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Auth no encontrado con email: " + email));

        return toDTO(auth);
    }

    public void desactivarAuth(Integer id){
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auth no encontrado con id:" + id));

        auth.setActivo(false);
        authRepository.save(auth);
    }

}
