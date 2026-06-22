package com.interaccion.interaccion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.interaccion.interaccion.dto.MatchDTO;
import com.interaccion.interaccion.model.Match;
import com.interaccion.interaccion.model.MatchStatus;
import com.interaccion.interaccion.repository.MatchRepository;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    void getTodosLosMatches_debeRetornarListaVaciaCuandoNoHayMatches() {
        when(matchRepository.findAll()).thenReturn(List.of());

        List<MatchDTO> resultado = matchService.getTodosLosMatches();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(matchRepository).findAll();
    }

    @Test
    void verificarYCrearMatch_debeCrearYRetornarTrueCuandoNoExiste() {
        when(matchRepository.findByPerfilAIdOrPerfilBId(1, 2)).thenReturn(List.of());
        
        Match matchGuardado = new Match();
        when(matchRepository.save(any(Match.class))).thenReturn(matchGuardado);

        boolean resultado = matchService.verificarYCrearMatch(1, 2);

        assertTrue(resultado);
        verify(matchRepository).findByPerfilAIdOrPerfilBId(1, 2);
        verify(matchRepository).save(any(Match.class));
    }

    @Test
    void verificarYCrearMatch_debeRetornarFalseCuandoMatchYaExiste() {
        Match matchExistente = new Match();
        matchExistente.setPerfilAId(1);
        matchExistente.setPerfilBId(2);

        when(matchRepository.findByPerfilAIdOrPerfilBId(1, 2)).thenReturn(List.of(matchExistente));

        boolean resultado = matchService.verificarYCrearMatch(1, 2);

        assertFalse(resultado);
        verify(matchRepository).findByPerfilAIdOrPerfilBId(1, 2);
        verify(matchRepository, never()).save(any(Match.class));
    }

    @Test
    void deshacerMatch_debeMarcarComoUnmatched() {
        Match match = new Match();
        match.setId(1);

        when(matchRepository.findById(1)).thenReturn(Optional.of(match));
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        matchService.deshacerMatch(1);

        assertEquals(MatchStatus.UNMATCHED, match.getStatus());
        verify(matchRepository).findById(1);
        verify(matchRepository).save(match);
    }
}