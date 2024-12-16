package com.alura.forohub.forohub.topico;

import java.time.LocalDateTime;
import java.util.List;

import com.alura.forohub.forohub.respuesta.DatosDetalleRespuesta;

public record DatosDetalleTopico (
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    List<DatosDetalleRespuesta> respuestas
) {
    
}
