package com.usuario.usuarios.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuarios.dto.RegionDTO;
import com.usuario.usuarios.service.RegionService;

@RestController
@RequestMapping("/api/v2/regiones")
public class RegionController {

    private static final Logger log = LoggerFactory.getLogger(RegionController.class);

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarTodo() {
        log.info("GET /api/v2/regiones");
        return ResponseEntity.ok(regionService.listarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> buscarPorId(@PathVariable Integer id) {
        log.info("GET /api/v2/regiones/{}", id);
        return ResponseEntity.ok(regionService.buscarPorId(id));
    }
}
