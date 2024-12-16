package com.alura.forohub.forohub.curso;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("""
           SELECT c FROM Curso c WHERE LOWER(c.nombre) LIKE CONCAT('%', LOWER(?1), '%')
            """)
    Optional<Curso> findByNombre(String nombreCurso);

}
