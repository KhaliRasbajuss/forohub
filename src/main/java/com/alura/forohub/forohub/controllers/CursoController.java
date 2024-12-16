package com.alura.forohub.forohub.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.forohub.forohub.curso.CursoService;
import com.alura.forohub.forohub.curso.DatosDetalleCurso;
import com.alura.forohub.forohub.curso.DatosRegistroCurso;
import com.alura.forohub.forohub.errors.NotRequestBodyException;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService service;

    @PostMapping("/registrar")
    public ResponseEntity<DatosDetalleCurso> registrarCurso(@RequestBody(required = false) @Valid DatosRegistroCurso datos, UriComponentsBuilder uriComponentsBuilder) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        var curso = service.registrarCurso(datos);
        URI uri = uriComponentsBuilder.path("/curso/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleCurso(curso.getId(), curso.getNombre(), curso.getCategoria()));
    }
    
    @GetMapping
    public ResponseEntity<?> findAllCursos() {
        var cursos = service.findAllCursos();
        System.out.println(cursos);
        List<DatosDetalleCurso> cursosList = cursos.stream().map(c -> new DatosDetalleCurso(c.getId(), c.getNombre(), c.getCategoria())).toList();
        return ResponseEntity.ok(cursosList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> Datos(@PathVariable Long id) {
        var curso = service.findById(id);
        if (curso == null) {
            Map<String, Object> json = new HashMap<>();
            json.put("status", HttpStatus.NOT_FOUND.value());
            json.put("message", "No existe el curso por el id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
        }
        return ResponseEntity.ok(new DatosDetalleCurso(curso.getId(), curso.getNombre(), curso.getCategoria()));
    }
     

    
}
