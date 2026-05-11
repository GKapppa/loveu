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

import com.loveu.loveu.dto.FotoPerfilDTO;
import com.loveu.loveu.service.FotoPerfilService;

@RestController
@RequestMapping("/api/fotoperfil")
public class FotoPerfilController {
    private static final Logger log = LoggerFactory.getLogger(FotoPerfilController.class);

    @Autowired
    private FotoPerfilService fotoPerfilService;

    @PostMapping
    public ResponseEntity<FotoPerfilDTO> crearFoto(@RequestBody FotoPerfilDTO fotoPerfilDTO){
        log.info("POST /api/fotoperfil");
        return ResponseEntity.status(HttpStatus.CREATED).body(fotoPerfilService.crearFotoPerfil(fotoPerfilDTO));
    }

    // Lista las fotos visibles de un perfil.
    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<FotoPerfilDTO>> listarPorPerfil(@PathVariable Integer perfilId){
        log.info("GET /api/fotoperfil/perfil/{}", perfilId);
        return ResponseEntity.ok(fotoPerfilService.listarPorPerfil(perfilId));
    }

    // Esta foto seria la primera que se muestra al hacer swipe.
    @GetMapping("/perfil/{perfilId}/principal")
    public ResponseEntity<FotoPerfilDTO> buscarPrincipal(@PathVariable Integer perfilId){
        log.info("GET /api/fotoperfil/perfil/{}/principal", perfilId);
        return ResponseEntity.ok(fotoPerfilService.buscarPrincipal(perfilId));
    }

    @PutMapping("/{fotoId}/principal")
    public ResponseEntity<FotoPerfilDTO> marcarComoPrincipal(@PathVariable Integer fotoId){
        log.info("PUT /api/fotoperfil/{}/principal", fotoId);
        return ResponseEntity.ok(fotoPerfilService.marcarComoPrincipal(fotoId));
    }

    // No se borra real, solo se deja inactiva.
    @DeleteMapping("/{fotoId}")
    public ResponseEntity<Void> desactivarFoto(@PathVariable Integer fotoId){
        log.info("DELETE /api/fotoperfil/{}", fotoId);
        fotoPerfilService.desactivarFoto(fotoId);
        return ResponseEntity.noContent().build();
    }
}
