package com.loveu.loveu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.MensajeDTO;
import com.loveu.loveu.model.Mensaje;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.repository.MensajeRepository;
import com.loveu.loveu.repository.PerfilRepository;

@Service
public class ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public MensajeDTO enviarMensaje(MensajeDTO dto) {
 
        if (dto.getPerfilEmisorId().equals(dto.getPerfilReceptorId())) {
            throw new RuntimeException("El emisor y el receptor no pueden ser el mismo perfil");
        }
        Perfil perfilEmisor = perfilRepository.findById(dto.getPerfilEmisorId())
            .orElseThrow(() -> new RuntimeException("Perfil emisor no encontrado: " + dto.getPerfilEmisorId()));

        Perfil perfilReceptor = perfilRepository.findById(dto.getPerfilReceptorId())
            .orElseThrow(() -> new RuntimeException("Perfil receptor no encontrado: " + dto.getPerfilReceptorId()));

        Mensaje mensaje = Mensaje.builder()
            .perfilEmisor(perfilEmisor)
            .perfilReceptor(perfilReceptor)
            .contenido(dto.getContenido())
            .build();

        mensaje = mensajeRepository.save(mensaje);
        log.info("Mensaje id={} guardado", mensaje.getId());
        return toDTO(mensaje);
    }

    public List<MensajeDTO> getMensajesPorMatch(Integer matchId) {
        log.info("Obteniendo mensajes para matchId={}", matchId);
        return mensajeRepository.findByMatchId(matchId)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MensajeDTO> getMensajesNoLeidos(Integer perfilReceptorId) {
        log.info("Obteniendo mensajes no leídos para perfilReceptorId={}", perfilReceptorId);
        return mensajeRepository.findAll().stream()
            .filter(m -> m.getPerfilReceptor().getId().equals(perfilReceptorId) && !m.isRead())
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public void marcarComoLeido(Integer mensajeId) {
        log.info("Marcando mensaje id={} como leído", mensajeId);
        Mensaje msg = mensajeRepository.findById(mensajeId)
            .orElseThrow(() -> new RuntimeException("Mensaje no encontrado: " + mensajeId));
        msg.setRead(true);
        mensajeRepository.save(msg);
    }

    public void eliminarMensaje(Integer mensajeId) {
        log.info("Eliminando mensaje id={}", mensajeId);
        mensajeRepository.deleteById(mensajeId);
    }

    private MensajeDTO toDTO(Mensaje m) {
        return MensajeDTO.builder()
            .perfilEmisorId(m.getPerfilEmisor().getId())
            .perfilReceptorId(m.getPerfilReceptor().getId())
            .contenido(m.getContenido())
            .sentAt(m.getSentAt())
            .read(m.isRead())
            .build();
    }
}
