package com.usuario.usuarios.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.usuario.usuarios.dto.PreferenciaDTO;
import com.usuario.usuarios.exception.ResourceNotFoundException;
import com.usuario.usuarios.model.Preferencia;
import com.usuario.usuarios.repository.PreferenciaRepository;

import reactor.core.publisher.Mono;

@Service
public class PreferenciaService {

    private static final Logger log = LoggerFactory.getLogger(PreferenciaService.class);

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    @Autowired
    private UsuarioValidaciones validaciones;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private PreferenciaDTO toDTO(Preferencia p) {
        return PreferenciaDTO.builder()
                .id(p.getId())
                .perfilId(p.getPerfilId())
                .edadMin(p.getEdadMin())
                .edadMax(p.getEdadMax())
                .distanciaMaxKm(p.getDistanciaMaxKm())
                .alturaMinCm(p.getAlturaMinCm())
                .alturaMaxCm(p.getAlturaMaxCm())
                .generoPreferido(p.getGeneroPreferido())
                .build();
    }

    public PreferenciaDTO crearPreferencia(PreferenciaDTO dto) {
        log.info("[v2] Creando preferencia para perfilId={}", dto.getPerfilId());

        validarPerfilExisteViaRest(dto.getPerfilId());

        try {
            validaciones.validarUnicaPreferenciaPorPerfil(dto.getPerfilId());
        } catch (RuntimeException e) {
            log.error("[v2] Perfil ya tiene preferencia: {}", e.getMessage());
            throw new RuntimeException("Este perfil ya tiene una preferencia creada");
        }

        try {
            validarEdades(dto.getEdadMin(), dto.getEdadMax());
        } catch (RuntimeException e) {
            log.error("[v2] Error validando edades: {}", e.getMessage());
            throw e;
        }

        try {
            validarAlturas(dto.getAlturaMinCm(), dto.getAlturaMaxCm());
        } catch (RuntimeException e) {
            log.error("[v2] Error validando alturas: {}", e.getMessage());
            throw e;
        }

        try {
            validarGenero(dto.getGeneroPreferido());
        } catch (RuntimeException e) {
            log.error("[v2] Error validando genero: {}", e.getMessage());
            throw e;
        }

        Preferencia preferencia = Preferencia.builder()
                .perfilId(dto.getPerfilId())
                .edadMin(dto.getEdadMin())
                .edadMax(dto.getEdadMax())
                .distanciaMaxKm(dto.getDistanciaMaxKm())
                .alturaMinCm(dto.getAlturaMinCm())
                .alturaMaxCm(dto.getAlturaMaxCm())
                .generoPreferido(dto.getGeneroPreferido())
                .activo(true)
                .build();

        try {
            preferencia = preferenciaRepository.save(preferencia);
            log.info("[v2] Preferencia creada exitosamente id={}", preferencia.getId());
            return toDTO(preferencia);
        } catch (Exception e) {
            log.error("[v2] Error al guardar preferencia: {}", e.getMessage());
            throw new RuntimeException("Error al guardar la preferencia en la base de datos");
        }
    }

    public List<PreferenciaDTO> listarTodas() {
        log.info("[v2] Listando todas las preferencias activas");
        try {
            return preferenciaRepository.findAll()
                    .stream()
                    .filter(Preferencia::isActivo)
                    .map(this::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("[v2] Error al listar preferencias: {}", e.getMessage());
            throw new RuntimeException("Error al obtener la lista de preferencias");
        }
    }

    public PreferenciaDTO buscarPorId(Integer id) {
        log.info("[v2] Buscando preferencia por id={}", id);
        try {
            Preferencia preferencia = validaciones.validarPreferenciaExiste(id);
            return toDTO(preferencia);
        } catch (RuntimeException e) {
            log.error("[v2] Preferencia no encontrada: {}", e.getMessage());
            throw new RuntimeException("Preferencia no encontrada con id: " + id);
        }
    }

    public PreferenciaDTO buscarPorPerfil(Integer perfilId) {
        log.info("[v2] Buscando preferencia por perfilId={}", perfilId);
        try {
            validaciones.validarPerfilExiste(perfilId);
        } catch (RuntimeException e) {
            log.error("[v2] Perfil no existe: {}", e.getMessage());
            throw new RuntimeException("Perfil no encontrado con id: " + perfilId);
        }

        try {
            return preferenciaRepository.findByPerfilIdAndActivoTrue(perfilId)
                    .map(this::toDTO)
                    .orElseThrow(() -> new RuntimeException("Preferencia no encontrada para perfil: " + perfilId));
        } catch (RuntimeException e) {
            log.error("[v2] Error al buscar preferencia por perfil: {}", e.getMessage());
            throw e;
        }
    }

    public PreferenciaDTO actualizarPreferencia(Integer id, PreferenciaDTO dto) {
        log.info("[v2] Actualizando preferencia id={}", id);
        Preferencia preferencia;
        try {
            preferencia = validaciones.validarPreferenciaExiste(id);
        } catch (RuntimeException e) {
            log.error("[v2] Preferencia no encontrada: {}", e.getMessage());
            throw new RuntimeException("Preferencia no encontrada con id: " + id);
        }

        if (dto.getEdadMin() != null || dto.getEdadMax() != null) {
            Integer edadMin = dto.getEdadMin() != null ? dto.getEdadMin() : preferencia.getEdadMin();
            Integer edadMax = dto.getEdadMax() != null ? dto.getEdadMax() : preferencia.getEdadMax();
            try {
                validarEdades(edadMin, edadMax);
            } catch (RuntimeException e) {
                log.error("[v2] Error validando edades en actualizacion: {}", e.getMessage());
                throw e;
            }
        }

        if (dto.getAlturaMinCm() != null || dto.getAlturaMaxCm() != null) {
            Integer alturaMin = dto.getAlturaMinCm() != null ? dto.getAlturaMinCm() : preferencia.getAlturaMinCm();
            Integer alturaMax = dto.getAlturaMaxCm() != null ? dto.getAlturaMaxCm() : preferencia.getAlturaMaxCm();
            try {
                validarAlturas(alturaMin, alturaMax);
            } catch (RuntimeException e) {
                log.error("[v2] Error validando alturas en actualizacion: {}", e.getMessage());
                throw e;
            }
        }

        if (dto.getGeneroPreferido() != null) {
            try {
                validarGenero(dto.getGeneroPreferido());
            } catch (RuntimeException e) {
                log.error("[v2] Error validando genero en actualizacion: {}", e.getMessage());
                throw e;
            }
        }

        try {
            if (dto.getPerfilId() != null) preferencia.setPerfilId(dto.getPerfilId());
            if (dto.getEdadMin() != null) preferencia.setEdadMin(dto.getEdadMin());
            if (dto.getEdadMax() != null) preferencia.setEdadMax(dto.getEdadMax());
            if (dto.getDistanciaMaxKm() != null) preferencia.setDistanciaMaxKm(dto.getDistanciaMaxKm());
            if (dto.getAlturaMinCm() != null) preferencia.setAlturaMinCm(dto.getAlturaMinCm());
            if (dto.getAlturaMaxCm() != null) preferencia.setAlturaMaxCm(dto.getAlturaMaxCm());
            if (dto.getGeneroPreferido() != null) preferencia.setGeneroPreferido(dto.getGeneroPreferido());

            preferencia = preferenciaRepository.save(preferencia);
            log.info("[v2] Preferencia actualizada exitosamente id={}", preferencia.getId());
            return toDTO(preferencia);
        } catch (Exception e) {
            log.error("[v2] Error al actualizar preferencia: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar la preferencia en la base de datos");
        }
    }

    public void desactivarPreferencia(Integer id) {
        log.info("[v2] Desactivando preferencia id={}", id);
        Preferencia preferencia;
        try {
            preferencia = validaciones.validarPreferenciaExiste(id);
        } catch (RuntimeException e) {
            log.error("[v2] Preferencia no encontrada: {}", e.getMessage());
            throw new RuntimeException("Preferencia no encontrada con id: " + id);
        }

        try {
            preferencia.setActivo(false);
            preferenciaRepository.save(preferencia);
            log.info("[v2] Preferencia desactivada exitosamente id={}", id);
        } catch (Exception e) {
            log.error("[v2] Error al desactivar preferencia: {}", e.getMessage());
            throw new RuntimeException("Error al desactivar la preferencia en la base de datos");
        }
    }

    private void validarPerfilExisteViaRest(Integer perfilId) {
        log.info("[v2] Verificando perfil via REST: perfilId={}", perfilId);
        try {
            HttpStatusCode status = webClientBuilder.build()
                    .get()
                    .uri("lb://usuarios/api/v2/perfiles/" + perfilId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.empty())
                    .toBodilessEntity()
                    .block()
                    .getStatusCode();

            if (status == null || !status.is2xxSuccessful()) {
                log.error("[v2] Perfil no existe via REST: perfilId={}", perfilId);
                throw new ResourceNotFoundException("Perfil no encontrado con id: " + perfilId);
            }
            log.info("[v2] Perfil verificado via REST: perfilId={}", perfilId);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("[v2] Error al verificar perfil via REST: {}", e.getMessage());
            throw new ResourceNotFoundException("Perfil no encontrado via REST: " + perfilId);
        }
    }

    private void validarEdades(Integer edadMin, Integer edadMax) {
        if (edadMin == null) {
            throw new RuntimeException("La edad minima es obligatoria");
        }
        if (edadMax == null) {
            throw new RuntimeException("La edad maxima es obligatoria");
        }
        if (edadMin < 18) {
            throw new RuntimeException("La edad minima debe ser al menos 18 anos");
        }
        if (edadMin > 99) {
            throw new RuntimeException("La edad minima no puede superar 99 anos");
        }
        if (edadMax < 18) {
            throw new RuntimeException("La edad maxima debe ser al menos 18 anos");
        }
        if (edadMax > 99) {
            throw new RuntimeException("La edad maxima no puede superar 99 anos");
        }
        if (edadMin > edadMax) {
            throw new RuntimeException("La edad minima no puede ser mayor que la edad maxima");
        }
    }

    private void validarAlturas(Integer alturaMinCm, Integer alturaMaxCm) {
        if (alturaMinCm != null && alturaMaxCm != null) {
            if (alturaMinCm < 100) {
                throw new RuntimeException("La altura minima debe ser al menos 100 cm");
            }
            if (alturaMinCm > 250) {
                throw new RuntimeException("La altura minima no puede superar 250 cm");
            }
            if (alturaMaxCm < 100) {
                throw new RuntimeException("La altura maxima debe ser al menos 100 cm");
            }
            if (alturaMaxCm > 250) {
                throw new RuntimeException("La altura maxima no puede superar 250 cm");
            }
            if (alturaMinCm > alturaMaxCm) {
                throw new RuntimeException("La altura minima no puede ser mayor que la altura maxima");
            }
        }
    }

    private void validarGenero(String genero) {
        if (genero == null || genero.isBlank()) {
            throw new RuntimeException("El genero preferido es obligatorio");
        }
        String generoNormalizado = genero.trim();
        if (!generoNormalizado.equalsIgnoreCase("Masculino")
                && !generoNormalizado.equalsIgnoreCase("Femenino")
                && !generoNormalizado.equalsIgnoreCase("No binario")
                && !generoNormalizado.equalsIgnoreCase("Todos")) {
            throw new RuntimeException("El genero preferido debe ser: Masculino, Femenino, No binario o Todos");
        }
    }
}
