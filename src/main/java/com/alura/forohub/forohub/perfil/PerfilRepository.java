package com.alura.forohub.forohub.perfil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{
    Optional<Perfil> findByNombre(String nombre);

    Page<Perfil> findAll(Pageable paginacion);
}
