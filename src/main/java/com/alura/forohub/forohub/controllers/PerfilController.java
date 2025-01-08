package com.alura.forohub.forohub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.forohub.forohub.errors.NotRequestBodyException;
import com.alura.forohub.forohub.perfil.DatosDetallePerfil;
import com.alura.forohub.forohub.perfil.DatosRegistrarPerfil;
import com.alura.forohub.forohub.perfil.PerfilService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/perfil")
@SecurityRequirement(name = "bearer-key")
public class PerfilController {


    @Autowired
    private PerfilService service;

    @PostMapping
    public ResponseEntity<DatosDetallePerfil> registrarPerfil(@RequestBody(required = false) @Valid DatosRegistrarPerfil datos, UriComponentsBuilder uriComponentsBuilder) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        var perfil = service.registrarPerfi(datos);
        URI uri = uriComponentsBuilder.path("/perfil/{id}").buildAndExpand(perfil.id()).toUri();
        return ResponseEntity.created(uri).body(perfil);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosDetallePerfil> getPerfilById(@PathVariable Long id) {
        var perfil = service.findById(id);
        return ResponseEntity.ok(perfil);
    }
    
    @GetMapping
    public ResponseEntity<Page<DatosDetallePerfil>> getAllPerfil(@PageableDefault(size = 5) Pageable paginacion) {
        var perfil = service.findAll(paginacion);
        return ResponseEntity.ok(perfil);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DatosDetallePerfil> deletePerfilById(@PathVariable Long id) {
        var perfil = service.remove(id);
        return ResponseEntity.ok(perfil);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DatosDetallePerfil> updatePerfil(@RequestBody(required = false) @Valid DatosRegistrarPerfil datos, @PathVariable Long id) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        var perfil = service.updatePerfil(datos, id);
        return ResponseEntity.ok(perfil);
    }
    
    
}
