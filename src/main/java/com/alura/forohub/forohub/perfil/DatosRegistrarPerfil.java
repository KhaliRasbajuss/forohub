package com.alura.forohub.forohub.perfil;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistrarPerfil(
    @NotBlank
    String nombre
) {

}
