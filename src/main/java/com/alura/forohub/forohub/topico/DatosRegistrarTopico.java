package com.alura.forohub.forohub.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistrarTopico(
    @NotBlank
    String mensaje,
    @NotBlank        
    String nombreCurso,
    @NotBlank
    String titulo
) {
}
