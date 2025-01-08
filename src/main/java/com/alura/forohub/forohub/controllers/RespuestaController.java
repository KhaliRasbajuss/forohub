package com.alura.forohub.forohub.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.forohub.forohub.errors.NotRequestBodyException;
import com.alura.forohub.forohub.respuesta.DatosActualizarRespuesta;
import com.alura.forohub.forohub.respuesta.DatosDetalleRespuesta;
import com.alura.forohub.forohub.respuesta.DatosRegistrarRespuesta;
import com.alura.forohub.forohub.respuesta.RespuestaService;
import com.alura.forohub.forohub.security.TokenService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/respuesta")   
public class RespuestaController {

    @Autowired
    private RespuestaService service;

    @Autowired
    private TokenService tokenService;

    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    public ResponseEntity<DatosDetalleRespuesta> registrarRespuesta(@RequestHeader("Authorization") String token, @RequestBody(required = false) @Valid DatosRegistrarRespuesta datos, UriComponentsBuilder uriComponentsBuilder) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        token = token.replace("Bearer ", "");
        Long userId = tokenService.getUsuarioId(token);
        var respuesta = service.registrarRespuesta(datos, userId);
        URI uri = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(uri).body(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleRespuesta> findByIdRespuesta(@PathVariable Long id) {
        var topico = service.findById(id);
        return ResponseEntity.ok(topico);
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/todos")
    public ResponseEntity<Page<DatosDetalleRespuesta>> findAllByIdRespuesta(@RequestHeader("Authorization") String token, @PageableDefault(size = 10) Pageable paginacion) {
        token = token.replace("Bearer ", "");
        Long userId = tokenService.getUsuarioId(token);
        var topicos = service.findAll(paginacion, userId);
        return ResponseEntity.ok(topicos);
    }
    
    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<DatosDetalleRespuesta> updateRespuestaById(@RequestHeader("Authorization") String token,
            @PathVariable Long id, @RequestBody(required = false) @Valid DatosActualizarRespuesta datos) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        token = token.replace("Bearer ", "");
        Long userId = tokenService.getUsuarioId(token);
        var respuesta = service.updateRespuestaById(id, userId, datos);
        return ResponseEntity.ok(respuesta);
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/eliminar/{id}")
     public ResponseEntity<DatosDetalleRespuesta> removeRespuestaById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        token = token.replace("Bearer ", "");
        Long userId = tokenService.getUsuarioId(token);
        var respuesta = service.removeRespuestaById(id, userId);
        return ResponseEntity.ok(respuesta);
    }
    

}
