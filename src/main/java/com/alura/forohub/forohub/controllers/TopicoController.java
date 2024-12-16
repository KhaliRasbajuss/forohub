package com.alura.forohub.forohub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.forohub.forohub.errors.NotRequestBodyException;
import com.alura.forohub.forohub.security.TokenService;
import com.alura.forohub.forohub.topico.DatosActualizarTopico;
import com.alura.forohub.forohub.topico.DatosDetalleTopico;
import com.alura.forohub.forohub.topico.DatosRegistrarTopico;
import com.alura.forohub.forohub.topico.TopicoService;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/topico")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosDetalleTopico> registrarTopico(@RequestHeader("Authorization") String token, @RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriComponentsBuilder) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        token = token.replace("Bearer ", "");
        Long userId = tokenService.getUsuarioId(token);
        var topico = service.registrarTopico(datos, userId);
        URI uri = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> getTopicoById(@PathVariable Long id) {
        var topico = service.findById(id);
        return ResponseEntity.ok(topico);
    }
    

    @GetMapping("/todos")
    public ResponseEntity<Page<DatosDetalleTopico>> getAllTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        var topicos = service.getAllTopicos(paginacion);
        return ResponseEntity.ok(topicos);
    }


    @PutMapping("/modificar/{id}")
    public ResponseEntity<DatosDetalleTopico> updateTopicoById(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody(required = false) @Valid DatosActualizarTopico datos) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        token = token.replace("Bearer ", "");
        Long userId = tokenService.getUsuarioId(token);
        var topico = service.updateTopicoById(id, userId, datos);
        return ResponseEntity.ok(topico);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<DatosDetalleTopico> removeTopicoById(@RequestHeader("Authorization") String token, @PathVariable Long id){
        token = token.replace("Bearer ", "");
        System.out.println(token);
        Long userId = tokenService.getUsuarioId(token);
        var topico = service.removeTopicoById(id, userId);
        return ResponseEntity.ok(topico);
    }
}
