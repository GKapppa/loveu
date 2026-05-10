package com.loveu.loveu.service;

import com.loveu.loveu.dto.NotificacionDTO;
import com.loveu.loveu.model.Notificacion;
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

    public NotificacionDTO crearNotificacion(NotificacionDTO dto) {
        log.info("Creando notificacion tipo={} para perfilDestinatarioId={}", dto.getType(), dto.getPerfilDestinatarioId());

        Perfil perfilDestinatario = perfilRepository.findById(dto.getPerfilDestinatarioId())
            .orElseThrow(() -> new RuntimeException("Perfil destinatario no encontrado: " + dto.getPerfilDestinatarioId()));

        Notificacion notificacion = Notificacion.builder()
            .perfilDestinatario(perfilDestinatario)
            .type(dto.getType())
            .message(dto.getMessage())
            .build();

        notificacion = notificacionRepository.save(notificacion);
        return toDTO(notificacion);
    }

    public List<NotificacionDTO> getNotificacionesPorPerfil(Integer perfilDestinatarioId) {
        log.info("Obteniendo notificaciones para perfilDestinatarioId={}", perfilDestinatarioId);
        return notificacionRepository.findByPerfilDestinatarioIdAndReadFalse(perfilDestinatarioId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<NotificacionDTO> getNoLeidas(Integer perfilDestinatarioId) {
        log.info("Obteniendo notificaciones no leidas para perfilDestinatarioId={}", perfilDestinatarioId);
        return notificacionRepository.findByPerfilDestinatarioIdAndReadFalse(perfilDestinatarioId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Integer contarNoLeidas(Integer perfilDestinatarioId) {
        Integer count = notificacionRepository.findByPerfilDestinatarioIdAndReadFalse(perfilDestinatarioId).size();
        log.info("Perfil id={} tiene {} notificaciones no leidas", perfilDestinatarioId, count);
        return count;
    }

    public void marcarComoLeida(Integer notificacionId) {
        log.info("Marcando notificacion id={} como leida", notificacionId);
        Notificacion notificacion = notificacionRepository.findById(notificacionId)
            .orElseThrow(() -> new RuntimeException("Notificacion no encontrada: " + notificacionId));

        notificacion.setRead(true);
        notificacionRepository.save(notificacion);
    }

    public void eliminarNotificacion(Integer notificacionId) {
        log.info("Eliminando notificacion id={}", notificacionId);
        notificacionRepository.deleteById(notificacionId);
    }

    private NotificacionDTO toDTO(Notificacion notificacion) {
        return NotificacionDTO.builder()
            .perfilDestinatarioId(notificacion.getPerfilDestinatario().getId())
            .type(notificacion.getType())
            .message(notificacion.getMessage())
            .read(notificacion.isRead())
            .createdAt(notificacion.getCreatedAt())
            .build();
    }
}
