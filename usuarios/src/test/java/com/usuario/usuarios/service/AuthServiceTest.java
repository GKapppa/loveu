package com.usuario.usuarios.service;

import com.usuario.usuarios.dto.AuthDTO;
import com.usuario.usuarios.dto.AuthRequestDTO;
import com.usuario.usuarios.model.Auth;
import com.usuario.usuarios.model.Usuario;
import com.usuario.usuarios.repository.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private UsuarioValidaciones validaciones;

    @InjectMocks
    private AuthService authService;

    private Auth auth;
    private AuthRequestDTO authRequestDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        auth = Auth.builder()
                .id(1)
                .email("diego@email.com")
                .password("123456")
                .rol("USER")
                .usuarioId(1)
                .activo(true)
                .build();

        authRequestDTO = AuthRequestDTO.builder()
                .email("diego@email.com")
                .password("123456")
                .rol("USER")
                .build();

        usuario = new Usuario();
        usuario.setId(1);
    }

    @Test
    void listarTodos_retornaListaDeAuthDTO() {
        when(authRepository.findAll()).thenReturn(List.of(auth));

        List<AuthDTO> resultado = authService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("diego@email.com", resultado.get(0).getEmail());
        assertEquals("USER", resultado.get(0).getRol());
        verify(authRepository).findAll();
    }

    @Test
    void listarTodos_retornaListaVaciaSiNoHayRegistros() {
        when(authRepository.findAll()).thenReturn(List.of());

        List<AuthDTO> resultado = authService.listarTodos();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void crearAuth_guardaYRetornaAuthDTO() {
        doNothing().when(validaciones).validarEmailNoDuplicado(authRequestDTO.getEmail());
        when(validaciones.validarUsuarioExiste(1)).thenReturn(usuario);
        when(authRepository.save(any(Auth.class))).thenReturn(auth);

        AuthDTO resultado = authService.crearAuth(1, authRequestDTO);

        assertEquals("diego@email.com", resultado.getEmail());
        assertEquals("USER", resultado.getRol());
        verify(authRepository).save(any(Auth.class));
    }

    @Test
    void crearAuth_lanzaExcepcionSiEmailYaExiste() {
        doThrow(new RuntimeException("Este email ya esta registrado"))
                .when(validaciones).validarEmailNoDuplicado(authRequestDTO.getEmail());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.crearAuth(1, authRequestDTO));

        assertEquals("Este email ya esta registrado", ex.getMessage());
        verify(authRepository, never()).save(any());
    }

    @Test
    void crearAuth_lanzaExcepcionSiUsuarioNoExiste() {
        doNothing().when(validaciones).validarEmailNoDuplicado(authRequestDTO.getEmail());
        when(validaciones.validarUsuarioExiste(99))
                .thenThrow(new RuntimeException("Usuario no encontrado con id: 99"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.crearAuth(99, authRequestDTO));

        assertTrue(ex.getMessage().contains("Usuario no encontrado"));
        verify(authRepository, never()).save(any());
    }

    @Test
    void login_retornaAuthDTOConCredencialesCorrectas() {
        when(validaciones.validarAuthExistePorEmail(authRequestDTO.getEmail())).thenReturn(auth);
        doNothing().when(validaciones).validarCredenciales(auth, authRequestDTO.getPassword());

        AuthDTO resultado = authService.login(authRequestDTO);

        assertEquals("diego@email.com", resultado.getEmail());
        assertEquals("USER", resultado.getRol());
    }

    @Test
    void login_lanzaExcepcionSiEmailNoExiste() {
        when(validaciones.validarAuthExistePorEmail(authRequestDTO.getEmail()))
                .thenThrow(new RuntimeException("Credenciales incorrectas"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.login(authRequestDTO));

        assertEquals("Credenciales incorrectas", ex.getMessage());
    }

    @Test
    void login_lanzaExcepcionSiPasswordEsIncorrecta() {
        when(validaciones.validarAuthExistePorEmail(authRequestDTO.getEmail())).thenReturn(auth);
        doThrow(new RuntimeException("Credenciales incorrectas"))
                .when(validaciones).validarCredenciales(auth, authRequestDTO.getPassword());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.login(authRequestDTO));

        assertEquals("Credenciales incorrectas", ex.getMessage());
    }

    @Test
    void login_lanzaExcepcionSiAuthEstaInactivo() {
        auth.setActivo(false);
        when(validaciones.validarAuthExistePorEmail(authRequestDTO.getEmail())).thenReturn(auth);
        doThrow(new RuntimeException("Credenciales incorrectas"))
                .when(validaciones).validarCredenciales(auth, authRequestDTO.getPassword());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.login(authRequestDTO));

        assertEquals("Credenciales incorrectas", ex.getMessage());
    }

    @Test
    void buscarPorEmail_retornaAuthDTOSiExiste() {
        when(validaciones.validarAuthExistePorEmail("diego@email.com")).thenReturn(auth);

        AuthDTO resultado = authService.buscarPorEmail("diego@email.com");

        assertEquals("diego@email.com", resultado.getEmail());
        assertEquals("USER", resultado.getRol());
    }

    @Test
    void buscarPorEmail_lanzaExcepcionSiNoExiste() {
        when(validaciones.validarAuthExistePorEmail("noexiste@email.com"))
                .thenThrow(new RuntimeException("Auth no encontrado con email: noexiste@email.com"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.buscarPorEmail("noexiste@email.com"));

        assertTrue(ex.getMessage().contains("Auth no encontrado con email"));
    }

    @Test
    void desactivarAuth_cambiaActivoAFalse() {
        when(validaciones.validarAuthExiste(1)).thenReturn(auth);
        when(authRepository.save(any(Auth.class))).thenReturn(auth);

        authService.desactivarAuth(1);

        assertFalse(auth.isActivo());
        verify(authRepository).save(auth);
    }

    @Test
    void desactivarAuth_lanzaExcepcionSiNoExiste() {
        when(validaciones.validarAuthExiste(99))
                .thenThrow(new RuntimeException("Auth no encontrado con id: 99"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.desactivarAuth(99));

        assertTrue(ex.getMessage().contains("Auth no encontrado con id"));
        verify(authRepository, never()).save(any());
    }
}
