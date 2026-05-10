package com.loveu.loveu.service;

import com.loveu.loveu.dto.NotificacionDTO;
import com.loveu.loveu.model.Notificacion;
import com.loveu.loveu.model.NotificacionType;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.repository.NotificacionRepository;
import com.loveu.loveu.repository.PerfilRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class NotificacionService {
    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public NotificacionDTO crearNotificacion(Integer perfilDestinatarioId, NotificacionType type, String message) {
        log.info("Creando notificación tipo={} para perfilDestinatarioId={}", type, perfilDestinatarioId);

        Perfil perfilDestinatario = perfilRepository.findById(perfilDestinatarioId)
            .orElseThrow(() -> new RuntimeException("Perfil destinatario no encontrado: " + perfilDestinatarioId));

        Notificacion n = Notificacion.builder()
            .perfilDestinatario(perfilDestinatario)
            .type(type)
            .message(message)
            // createdAt se asigna automáticamente en @PrePersist
            .build();

        n = notificacionRepository.save(n);
        log.info("Notificación id={} creada", n.getId());
        return toDTO(n);
    }

    public List<NotificacionDTO> getNotificacionesPorPerfil(Integer perfilDestinatarioId) {
        log.info("Obteniendo notificaciones para perfilDestinatarioId={}", perfilDestinatarioId);
        return notificacionRepository.findByPerfilDestinatarioIdAndReadFalse(perfilDestinatarioId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<NotificacionDTO> getNoLeidas(Integer perfilDestinatarioId) {
        log.info("Obteniendo notificaciones no leídas para perfilDestinatarioId={}", perfilDestinatarioId);
        return notificacionRepository.findByPerfilDestinatarioIdAndReadFalse(perfilDestinatarioId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Integer contarNoLeidas(Integer perfilDestinatarioId) {
        Integer count = notificacionRepository.findByPerfilDestinatarioIdAndReadFalse(perfilDestinatarioId).size();
        log.info("Perfil id={} tiene {} notificaciones no leídas", perfilDestinatarioId, count);
        return count;
    }

    public void marcarComoLeida(Integer notificacionId) {
        log.info("Marcando notificación id={} como leída", notificacionId);
        Notificacion n = notificacionRepository.findById(notificacionId)
            .orElseThrow(() -> new RuntimeException("Notificación no encontrada: " + notificacionId));
        n.setRead(true);
        notificacionRepository.save(n);
    }

    public void eliminarNotificacion(Integer notificacionId) {
        log.info("Eliminando notificación id={}", notificacionId);
        notificacionRepository.deleteById(notificacionId);
    }

    private NotificacionDTO toDTO(Notificacion n) {
        return NotificacionDTO.builder()
            .type(n.getType())
            .message(n.getMessage())
            .read(n.isRead())
            .createdAt(n.getCreatedAt())
            .build();
    }
}
