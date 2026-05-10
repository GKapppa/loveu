package com.loveu.loveu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loveu.loveu.dto.PreferenciaDTO;
import com.loveu.loveu.model.Perfil;
import com.loveu.loveu.model.Preferencia;
import com.loveu.loveu.repository.PerfilRepository;
import com.loveu.loveu.repository.PreferenciaRepository;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public PreferenciaDTO toDTO(Preferencia preferencia){
        return PreferenciaDTO.builder()
                .perfilId(preferencia.getPerfil().getId())
                .generoDeseado(preferencia.getGeneroDeseado())
                .edadMinima(preferencia.getEdadMinima())
                .edadMaxima(preferencia.getEdadMaxima())
                .distanciaMaximaKm(preferencia.getDistanciaMaximaKm())
                .build();
    }

    public List<PreferenciaDTO> listarTodas(){
        return preferenciaRepository.findAll().stream().map(this::toDTO).toList();
    }

    public PreferenciaDTO crearPreferencia(PreferenciaDTO dto){
        if (dto.getEdadMinima() > dto.getEdadMaxima()) {
            throw new RuntimeException("La edad minima no puede ser mayor a la edad maxima");
        }

        if (preferenciaRepository.existsByPerfilId(dto.getPerfilId())) {
            throw new RuntimeException("Este perfil ya tiene preferencias creadas");
        }

        Perfil perfil = perfilRepository.findById(dto.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        Preferencia preferencia = Preferencia.builder()
                .perfil(perfil)
                .generoDeseado(dto.getGeneroDeseado())
                .edadMinima(dto.getEdadMinima())
                .edadMaxima(dto.getEdadMaxima())
                .distanciaMaximaKm(dto.getDistanciaMaximaKm())
                .activo(true)
                .build();

        Preferencia preferenciaGuardada = preferenciaRepository.save(preferencia);
        return toDTO(preferenciaGuardada);
    }

    public PreferenciaDTO buscarPorPerfil(Integer perfilId){
        Preferencia preferencia = preferenciaRepository.findByPerfilId(perfilId)
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada para este perfil"));

        return toDTO(preferencia);
    }

    public PreferenciaDTO actualizarPreferencia(Integer perfilId, PreferenciaDTO dto){
        if (dto.getEdadMinima() > dto.getEdadMaxima()) {
            throw new RuntimeException("La edad minima no puede ser mayor a la edad maxima");
        }

        Preferencia preferencia = preferenciaRepository.findByPerfilId(perfilId)
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada para este perfil"));

        preferencia.setGeneroDeseado(dto.getGeneroDeseado());
        preferencia.setEdadMinima(dto.getEdadMinima());
        preferencia.setEdadMaxima(dto.getEdadMaxima());
        preferencia.setDistanciaMaximaKm(dto.getDistanciaMaximaKm());

        Preferencia preferenciaActualizada = preferenciaRepository.save(preferencia);
        return toDTO(preferenciaActualizada);
    }

    public void desactivarPreferencia(Integer perfilId){
        Preferencia preferencia = preferenciaRepository.findByPerfilId(perfilId)
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada para este perfil"));

        preferencia.setActivo(false);
        preferenciaRepository.save(preferencia);
    }
}
