package com.interaccion.interaccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interaccion.interaccion.model.Match;
import com.interaccion.interaccion.model.Mensaje;
import com.interaccion.interaccion.model.Notificacion;
import com.interaccion.interaccion.model.Swipe;
import com.interaccion.interaccion.repository.MatchRepository;
import com.interaccion.interaccion.repository.MensajeRepository;
import com.interaccion.interaccion.repository.NotificacionRepository;
import com.interaccion.interaccion.repository.SwipeRepository;

@Service
public class InteraccionValidaciones {

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    public Swipe validarSwipeExiste(Integer id) {
        return swipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Swipe no encontrado: " + id));
    }

    public void validarNoSelfSwipe(Integer origen, Integer destino) {
        if (origen.equals(destino)) {
            throw new RuntimeException("No puedes swipetearte a ti mismo");
        }
    }

    public void validarNoDuplicateSwipe(Integer origen, Integer destino) {
        if (swipeRepository.existsByPerfilOrigenIdAndPerfilDestinoId(origen, destino)) {
            throw new RuntimeException("Ya existe un swipe de este perfil origen hacia este destino");
        }
    }

    public Match validarMatchExiste(Integer id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match no encontrado: " + id));
    }

    public void validarNoSelfMatch(Integer perfilA, Integer perfilB) {
        if (perfilA.equals(perfilB)) {
            throw new RuntimeException("No puedes hacer match contigo mismo");
        }
    }

    public Mensaje validarMensajeExiste(Integer id) {
        return mensajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado: " + id));
    }

    public void validarNoSelfMensaje(Integer emisor, Integer receptor) {
        if (emisor.equals(receptor)) {
            throw new RuntimeException("No puedes enviarte un mensaje a ti mismo");
        }
    }

    public Notificacion validarNotificacionExiste(Integer id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada: " + id));
    }
}
