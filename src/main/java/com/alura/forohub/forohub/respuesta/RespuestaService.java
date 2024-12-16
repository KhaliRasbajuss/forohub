package com.alura.forohub.forohub.respuesta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alura.forohub.forohub.errors.NotFoundException;
import com.alura.forohub.forohub.errors.NotUpdateDataException;
import com.alura.forohub.forohub.topico.TopicoRepository;
import com.alura.forohub.forohub.usuario.UsuarioRepository;



@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public DatosDetalleRespuesta registrarRespuesta(DatosRegistrarRespuesta datos, Long userId) {
        var usuario = usuarioRepository.findById(userId).orElseThrow(() -> new NotFoundException("El usuario no existe"));
        var topico = topicoRepository.findById(datos.topicoId()).orElseThrow(() -> new NotFoundException("El topico por el id '" + datos.topicoId() + "' no existe"));
        var respuesta = repository.save(new Respuesta(datos, usuario, topico));
        return new DatosDetalleRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getUsuario().getNombre(), respuesta.getTopico().getTitulo());
    }

    public DatosDetalleRespuesta findById(Long id) {
        var respuesta = repository.findById(id).orElseThrow(() -> new NotFoundException("No existe ninguna respuesta por el id '" + id + "'"));
        return new DatosDetalleRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getUsuario().getNombre(), respuesta.getTopico().getTitulo());
    }

    
    public Page<DatosDetalleRespuesta> findAll(Pageable paginacion, Long userId) {
        var respuesta = repository.findAllByUsuarioId(paginacion, userId);
        return respuesta.map(r -> new DatosDetalleRespuesta(r.getId(), r.getMensaje(), r.getUsuario().getNombre(), r.getTopico().getTitulo()));
    }
    
    
    public DatosDetalleRespuesta updateRespuestaById(Long id, Long userId, DatosActualizarRespuesta datos) {
        var respuesta = repository.findById(id).orElseThrow(() -> new NotFoundException("No existe respuesta por el id "+ id));
        var usuario = usuarioRepository.findById(userId).orElseThrow(() -> new NotFoundException("No existe el usuario")); 
        if (respuesta.getUsuario().getId() != usuario.getId()) {
            throw new NotUpdateDataException("No se puede modificar esta respuesta ya que no eres el creador del mismo.");
        }
        respuesta.setMensaje(datos.mensaje());
        repository.save(respuesta);
        return new DatosDetalleRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getUsuario().getNombre(), respuesta.getTopico().getTitulo());
    }

    
    public DatosDetalleRespuesta removeRespuestaById(Long id, Long userId) {
        var respuesta = repository.findById(id).orElseThrow(() -> new NotFoundException("No existe respuesta por el id '"+ id + "' "));
        var usuario = usuarioRepository.findById(userId).orElseThrow(() -> new NotFoundException("No existe el usuario")); 
        if (respuesta.getUsuario().getId() != usuario.getId()) {
            throw new NotUpdateDataException("No se puede eliminar esta respuesta ya que no eres el creador del mismo.");
        }
        repository.delete(respuesta);
        return new DatosDetalleRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getUsuario().getNombre(), respuesta.getTopico().getTitulo());
    }

}
