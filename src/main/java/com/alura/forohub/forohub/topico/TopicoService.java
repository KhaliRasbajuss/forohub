package com.alura.forohub.forohub.topico;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alura.forohub.forohub.curso.CursoRepository;
import com.alura.forohub.forohub.errors.ClasesRelacionadasException;
import com.alura.forohub.forohub.errors.NotFoundException;
import com.alura.forohub.forohub.errors.NotUpdateDataException;
import com.alura.forohub.forohub.respuesta.DatosDetalleRespuesta;
import com.alura.forohub.forohub.usuario.UsuarioRepository;




@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DatosDetalleTopico registrarTopico(DatosRegistrarTopico datos, Long userId ) {

        var curso = cursoRepository.findByNombre(datos.nombreCurso()).orElseThrow(() -> new NotFoundException("Curso '"+ datos.nombreCurso()+ "'' no encontrado"));
        var usuario = usuarioRepository.findById(userId).orElseThrow(() -> new NotFoundException("No existe el usuario")); 
        var topico = repository.save(new Topico(datos, usuario, curso));
        List<DatosDetalleRespuesta> respuestas = topico.getRespuestas().stream().map(r -> new DatosDetalleRespuesta(r.getId(), r.getMensaje(), r.getUsuario().getNombre(), r.getTopico().getTitulo())).toList();
        return new DatosDetalleTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion() , respuestas);

    }

    public DatosDetalleTopico findById(Long id ) {
        var topico = repository.findById(id).orElseThrow(() -> new NotFoundException("No existe el topico por el id "+ id));
        List<DatosDetalleRespuesta> respuestas = topico.getRespuestas().stream().map(r -> new DatosDetalleRespuesta(r.getId(), r.getMensaje(), r.getUsuario().getNombre(), r.getTopico().getTitulo())).toList();
        return new DatosDetalleTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion() , respuestas);
    }

    public Page<DatosDetalleTopico> getAllTopicos(Pageable paginacion) {
        var topicos = repository.findAll(paginacion);
       Page<DatosDetalleTopico> detalles = topicos.map(topico -> {
            List<DatosDetalleRespuesta> respuestas = topico.getRespuestas().stream()
            .map(r -> 
                new DatosDetalleRespuesta(
                    r.getId(),
                    r.getMensaje(),
                    r.getUsuario().getNombre(),
                    r.getTopico().getTitulo()
                )
            ).toList();
            return new DatosDetalleTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                respuestas
            );
        });
        return detalles;
    }

    public DatosDetalleTopico updateTopicoById(Long id, Long userId, DatosActualizarTopico datos) {
        var topico = repository.findById(id).orElseThrow(() -> new NotFoundException("No existe el topico por el id "+ id));
        var usuario = usuarioRepository.findById(userId).orElseThrow(() -> new NotFoundException("No existe el usuario")); 
        if (topico.getUsuario().getId() != usuario.getId()) {
            throw new NotUpdateDataException("No se puede modificar este topico ya que no eres el creador del mismo.");
        }
        if (topico.getRespuestas().size() > 0) {
            throw new ClasesRelacionadasException("No se puede modificar este topico ya que existen respuestas relacionadas al mismo");
        }
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        repository.save(topico);
        List<DatosDetalleRespuesta> respuestas = topico.getRespuestas().stream().map(r -> new DatosDetalleRespuesta(r.getId(), r.getMensaje(), r.getUsuario().getNombre(), r.getTopico().getTitulo())).toList();
        return new DatosDetalleTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),topico.getFechaCreacion(), respuestas);
    }

    public DatosDetalleTopico removeTopicoById(Long id, Long userId) {
       var topico = repository.findById(id).orElseThrow(() -> new NotFoundException("No existe el topico por el id "+ id));
        var usuario = usuarioRepository.findById(userId).orElseThrow(() -> new NotFoundException("No existe el usuario")); 
        if (topico.getUsuario().getId() != usuario.getId()) {
            throw new NotUpdateDataException("No se puede eliminar este topico ya que no eres el creador del mismo.");
        }
        if (topico.getRespuestas().size() > 0) {
            throw new ClasesRelacionadasException("No se puede eliminar este topico ya que existen respuestas relacionadas al mismo");
        }
        repository.delete(topico);
        List<DatosDetalleRespuesta> respuestas = topico.getRespuestas().stream().map(r -> new DatosDetalleRespuesta(r.getId(), r.getMensaje(), r.getUsuario().getNombre(), r.getTopico().getTitulo())).toList();
        return new DatosDetalleTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),topico.getFechaCreacion(), respuestas);
    }
}