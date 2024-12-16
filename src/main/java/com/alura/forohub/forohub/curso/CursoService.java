package com.alura.forohub.forohub.curso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {


    @Autowired
    private CursoRepository repository;


    public Curso registrarCurso(DatosRegistroCurso datos) {
        return repository.save(new Curso(datos));
    }

    public Curso findById(Long id) {
        var curso = repository.findById(id);
        if (curso.isPresent()) {
            return curso.get();
        }
        return null;
    }

    public List<Curso> findAllCursos() {
        var cursos = (List<Curso>) repository.findAll();
        System.out.println("Cursos encontrados: " + cursos.size());
        return cursos;
    }
}
