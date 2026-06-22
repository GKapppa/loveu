package com.interaccion.interaccion.service;

import java.util.ArrayList;
import java.util.List;

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

    public NotificacionDTO crearNotificacion(Integer perfilDestinatarioId, String type, String message) {
        log.info("[v2] Creando notificacion: destinatario={} type={}", perfilDestinatarioId, type);

        Notificacion notificacion = Notificacion.builder()
            .perfilDestinatarioId(perfilDestinatarioId)
            .type(type)
            .message(message)
            .build();

        Notificacion guardado = notificacionRepository.save(notificacion);
        return convertirADTO(guardado);
    }

    public List<NotificacionDTO> getPorPerfilDestinatario(Integer perfilDestinatarioId) {
        log.info("[v2] Obteniendo notificaciones del perfilId={}", perfilDestinatarioId);
        List<NotificacionDTO> listaDTOs = new ArrayList<>();
        List<Notificacion> notificaciones = notificacionRepository.findByPerfilDestinatarioIdOrderByCreatedAtDesc(perfilDestinatarioId);
        for (Notificacion noti : notificaciones) {
            listaDTOs.add(convertirADTO(noti));
        }
        return listaDTOs;
    }

    public List<NotificacionDTO> getNoLeidas(Integer perfilDestinatarioId) {
        log.info("[v2] Obteniendo notificaciones no leidas de perfilId={}", perfilDestinatarioId);
        List<NotificacionDTO> listaDTOs = new ArrayList<>();
        List<Notificacion> notificaciones = notificacionRepository.findByPerfilDestinatarioIdAndLeidoFalse(perfilDestinatarioId);
        for (Notificacion noti : notificaciones) {
            listaDTOs.add(convertirADTO(noti));
        }
        return listaDTOs;
    }

    public void marcarComoLeida(Integer notificacionId) {
        log.info("[v2] Marcando notificacion id={} como leida", notificacionId);
        Notificacion notificacion = notificacionRepository.findById(notificacionId)
            .orElseThrow(() -> new RuntimeException("Notificacion no encontrada: " + notificacionId));
        
        notificacion.setLeido(true);
        notificacionRepository.save(notificacion);
    }

    private NotificacionDTO convertirADTO(Notificacion noti) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(noti.getId());
        dto.setPerfilDestinatarioId(noti.getPerfilDestinatarioId());
        dto.setType(noti.getType());
        dto.setMessage(noti.getMessage());
        dto.setLeido(noti.isLeido());
        dto.setCreatedAt(noti.getCreatedAt());
        return dto;
    }
}