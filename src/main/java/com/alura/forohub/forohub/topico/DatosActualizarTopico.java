package com.alura.forohub.forohub.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarTopico(
    @NotBlank
    String mensaje,
    @NotBlank
    String titulo
) {

}
