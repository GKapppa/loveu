package com.usuario.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComunaRepository  comunaRepository;

    public PerfilDTO crearPerfil(PerfilDTO dto){
         if (perfilRepository.findByUsuarioId(dto.getUsuarioId()).isPresent()){
            throw new RuntimeException("Este usuario ya posee un perfil creado!");
         }
         Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
         Comuna comuna = comunaRepository.findById(dto.getComunaId()).orElseThrow(() -> new RuntimeException("Comuna no encontrada!"));
         Perfil perfil = Perfil.builder()
         .nombreVisible(dto.getNombreVisible())
         .biografia(dto.getBiografia())
         .ocupacion(dto.getOcupacion())
         .alturaCm(dto.getAlturaCm())
         .activo(true)
         .usuario(usuario)
         .comuna(comuna)
         .build();

         perfil = perfilRepository.save(perfil);
         return toDTO(perfil);
    }

    public List<PerfilDTO>getTodos(){
        return perfilRepository.findAll().stream().map(this::toDTO).toList();
    }

    public PerfilDTO getPorId(Integer id){
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        return toDTO(perfil);
    }

    public PerfilDTO getPorUsuario(Integer usuarioId){
        Perfil perfil = perfilRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado para este usuario"));

        return toDTO(perfil);
    }

    public List<PerfilDTO> getPorComuna(Integer comunaId){
        return perfilRepository.findByComunaId(comunaId)
                .stream().map(this::toDTO).toList();
    }

    public PerfilDTO actualizarPerfil(Integer id, PerfilDTO dto){
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        perfil.setNombreVisible(dto.getNombreVisible());
        perfil.setBiografia(dto.getBiografia());
        perfil.setOcupacion(dto.getOcupacion());
        perfil.setAlturaCm(dto.getAlturaCm());

        if (dto.getComunaId() != null) {
            Comuna comuna = comunaRepository.findById(dto.getComunaId())
                    .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));

            perfil.setComuna(comuna);
        }

        perfil = perfilRepository.save(perfil);
        return toDTO(perfil);
    }

    public void desactivarPerfil(Integer id){
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        perfil.setActivo(false);
        perfilRepository.save(perfil);
    }

    public void eliminarPerfil(Integer id){
        perfilRepository.deleteById(id);
    }

    private PerfilDTO toDTO(Perfil perfil){
        return PerfilDTO.builder()
                .nombreVisible(perfil.getNombreVisible())
                .biografia(perfil.getBiografia())
                .ocupacion(perfil.getOcupacion())
                .alturaCm(perfil.getAlturaCm())
                .usuarioId(perfil.getUsuario().getId())
                .comunaId(perfil.getComuna().getId())
                .build();
    }
}
