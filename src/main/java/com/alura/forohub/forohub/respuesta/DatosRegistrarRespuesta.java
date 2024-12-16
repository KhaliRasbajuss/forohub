package com.alura.forohub.forohub.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistrarRespuesta(
    @NotNull
    Long topicoId,
    @NotBlank
    String mensaje
) {

}
