package com.usuario.usuarios.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuarios.dto.ComunaDTO;
import com.usuario.usuarios.service.ComunaService;

@RestController
@RequestMapping("/api/v2/comunas")
public class ComunaController {
    private static final Logger log = LoggerFactory.getLogger(ComunaController.class);

    @Autowired
    private ComunaService comunaService;

    // La comuna se usa para ubicar el perfil, no tiene mucha vuelta.
    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarTodo(){
        log.info("GET /api/v2/comunas");
        return ResponseEntity.ok(comunaService.listarTodo());
    }
}
