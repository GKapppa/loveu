package com.loveu.loveu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loveu.loveu.dto.RegionDTO;
import com.loveu.loveu.service.RegionService;

@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {
    private static final Logger log = LoggerFactory.getLogger(RegionController.class);

    @Autowired
    private RegionService regionService;

    // Las regiones son datos de apoyo, por eso solo las consultamos.
    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarTodos(){
        log.info("GET /api/v1/regiones");
        return ResponseEntity.ok(regionService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> buscarPorId(@PathVariable Integer id){
        log.info("GET /api/v1/regiones/{}", id);
        return ResponseEntity.ok(regionService.buscarPorId(id));
    }
}
