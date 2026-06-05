package com.loveu.loveu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.PreferenciaDTO;
import com.loveu.loveu.service.PreferenciaService;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciaController {
    private static final Logger log = LoggerFactory.getLogger(PreferenciaController.class);

    @Autowired
    private PreferenciaService preferenciaService;

    @PostMapping
    public ResponseEntity<PreferenciaDTO> crearPreferencia(@RequestBody PreferenciaDTO dto){
        log.info("POST /api/preferencias");

        if (dto == null) {
            throw new RuntimeException("Los datos de preferencia son obligatorios");
        }

        if (dto.getPerfilId() == null) {
            throw new RuntimeException("El perfilId es obligatorio");
        }

        if (dto.getEdadMinima() == null || dto.getEdadMaxima() == null) {
            throw new RuntimeException("Las edades son obligatorias");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(preferenciaService.crearPreferencia(dto));
    }

    @GetMapping
    public ResponseEntity<List<PreferenciaDTO>> listarTodas(){
        log.info("GET /api/preferencias");
        return ResponseEntity.ok(preferenciaService.listarTodas());
    }

    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<PreferenciaDTO> buscarPorPerfil(@PathVariable Integer perfilId){
        log.info("GET /api/preferencias/perfil/{}", perfilId);
        return ResponseEntity.ok(preferenciaService.buscarPorPerfil(perfilId));
    }

    @PutMapping("/perfil/{perfilId}")
    public ResponseEntity<PreferenciaDTO> actualizarPreferencia(
            @PathVariable Integer perfilId,
            @RequestBody PreferenciaDTO dto){
        log.info("PUT /api/preferencias/perfil/{}", perfilId);

        return ResponseEntity.ok(preferenciaService.actualizarPreferencia(perfilId, dto));
    }

    @DeleteMapping("/perfil/{perfilId}")
    public ResponseEntity<Void> desactivarPreferencia(@PathVariable Integer perfilId){
        log.info("DELETE /api/preferencias/perfil/{}", perfilId);
        preferenciaService.desactivarPreferencia(perfilId);
        return ResponseEntity.noContent().build();
    }
}
