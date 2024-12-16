package com.alura.forohub.forohub.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarRespuesta(
    @NotBlank
    String mensaje
) {

}
