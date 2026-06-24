package com.interaccion.interaccion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interaccion.interaccion.dto.NotificacionDTO;
import com.interaccion.interaccion.model.Notificacion;
import com.interaccion.interaccion.repository.NotificacionRepository;

@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private InteraccionValidaciones validaciones;

    public NotificacionDTO crearNotificacion(Integer perfilDestinatarioId, String type, String message) {
        log.info("[v2] Creando notificacion: destinatario={} type={}", perfilDestinatarioId, type);

        Notificacion notificacion = Notificacion.builder()
                .perfilDestinatarioId(perfilDestinatarioId)
                .type(type)
                .message(message)
                .build();

        Notificacion guardado = notificacionRepository.save(notificacion);
        return toDTO(guardado);
    }

    public List<NotificacionDTO> getPorPerfilDestinatario(Integer perfilDestinatarioId) {
        log.info("[v2] Obteniendo notificaciones del perfilId={}", perfilDestinatarioId);
        return notificacionRepository.findByPerfilDestinatarioIdOrderByCreatedAtDesc(perfilDestinatarioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<NotificacionDTO> getNoLeidas(Integer perfilDestinatarioId) {
        log.info("[v2] Obteniendo notificaciones no leidas de perfilId={}", perfilDestinatarioId);
        return notificacionRepository.findByPerfilDestinatarioIdAndLeidoFalse(perfilDestinatarioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void marcarComoLeida(Integer notificacionId) {
        log.info("[v2] Marcando notificacion id={} como leida", notificacionId);
        Notificacion notificacion = validaciones.validarNotificacionExiste(notificacionId);
        notificacion.setLeido(true);
        notificacionRepository.save(notificacion);
    }

    private NotificacionDTO toDTO(Notificacion n) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(n.getId());
        dto.setPerfilDestinatarioId(n.getPerfilDestinatarioId());
        dto.setType(n.getType());
        dto.setMessage(n.getMessage());
        dto.setLeido(n.isLeido());
        dto.setCreatedAt(n.getCreatedAt());
        return dto;
    }
}
