package com.alura.forohub.forohub.perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alura.forohub.forohub.errors.ClasesRelacionadasException;
import com.alura.forohub.forohub.errors.NotFoundException;


@Service
public class PerfilService {

    @Autowired
    private PerfilRepository repository;

    public DatosDetallePerfil registrarPerfi(DatosRegistrarPerfil datos) {
        var perfil = repository.save(new Perfil(datos));
        return new DatosDetallePerfil(perfil.getId(), perfil.getNombre());
    }

    public DatosDetallePerfil findById(Long id) {
        var perfil = repository.findById(id) .orElseThrow(() -> new NotFoundException("No existe el perfil '" + id + "'"));
        return new DatosDetallePerfil(perfil.getId(), perfil.getNombre());
    }

    public Page<DatosDetallePerfil> findAll(Pageable paginacion) {
        var perfil = repository.findAll(paginacion);
        return perfil.map(p -> new DatosDetallePerfil(p.getId(), p.getNombre()));
    }
    
    public DatosDetallePerfil updatePerfil(DatosRegistrarPerfil datos, Long id) {
        var perfil = repository.findById(id).orElseThrow(() -> new NotFoundException("No existe el perfil '" + id + "'"));
        perfil.setNombre(datos.nombre());
        if (perfil.getUsuarios().size() > 0) {
            throw new ClasesRelacionadasException("No se puede modificar el perfil porque tiene usuarios asociados");
        }
        repository.save(perfil);
        return new DatosDetallePerfil(perfil.getId(), perfil.getNombre());
    }
    
    public DatosDetallePerfil remove(Long id) {
        var perfil = repository.findById(id) .orElseThrow(() -> new NotFoundException("No existe el perfil '" + id + "'"));
        try {
            repository.delete(perfil);
            return new DatosDetallePerfil(perfil.getId(), perfil.getNombre());
        } catch (Exception e) {
            throw new ClasesRelacionadasException("No se puede eliminar el perfil porque tiene usuarios asociados");
        }
    }

}
