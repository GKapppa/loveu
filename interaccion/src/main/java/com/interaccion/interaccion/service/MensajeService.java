package com.interaccion.interaccion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interaccion.interaccion.dto.MensajeDTO;
import com.interaccion.interaccion.model.Mensaje;
import com.interaccion.interaccion.repository.MensajeRepository;

@Service
public class MensajeService {

    private static final Logger log = LoggerFactory.getLogger(MensajeService.class);

    @Autowired
    private MensajeRepository mensajeRepository;

    public MensajeDTO enviarMensaje(Integer matchId, Integer perfilEmisorId, Integer perfilReceptorId, String contenido) {
        log.info("[v2] Enviando mensaje: matchId={} emisor={} receptor={}", matchId, perfilEmisorId, perfilReceptorId);

        if (perfilEmisorId.equals(perfilReceptorId)) {
            throw new RuntimeException("No puedes enviarte un mensaje a ti mismo");
        }

        Mensaje mensaje = Mensaje.builder()
            .matchId(matchId)
            .perfilEmisorId(perfilEmisorId)
            .perfilReceptorId(perfilReceptorId)
            .contenido(contenido)
            .build();

        mensaje = mensajeRepository.save(mensaje);
        return toDTO(mensaje);
    }

    public List<MensajeDTO> getPorMatch(Integer matchId) {
        log.info("[v2] Obteniendo mensajes del matchId={}", matchId);
        return mensajeRepository.findByMatchIdOrderBySentAtAsc(matchId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MensajeDTO> getNoLeidos(Integer perfilReceptorId) {
        log.info("[v2] Obteniendo mensajes no leidos de perfilId={}", perfilReceptorId);
        return mensajeRepository.findByPerfilReceptorIdAndLeidoFalse(perfilReceptorId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void marcarComoLeido(Integer mensajeId) {
        log.info("[v2] Marcando mensaje id={} como leido", mensajeId);
        Mensaje mensaje = mensajeRepository.findById(mensajeId)
            .orElseThrow(() -> new RuntimeException("Mensaje no encontrado: " + mensajeId));
        mensaje.setLeido(true);
        mensajeRepository.save(mensaje);
    }

    private MensajeDTO toDTO(Mensaje m) {
        return MensajeDTO.builder()
            .id(m.getId())
            .matchId(m.getMatchId())
            .perfilEmisorId(m.getPerfilEmisorId())
            .perfilReceptorId(m.getPerfilReceptorId())
            .contenido(m.getContenido())
            .sentAt(m.getSentAt())
            .leido(m.isLeido())
            .build();
    }
}
