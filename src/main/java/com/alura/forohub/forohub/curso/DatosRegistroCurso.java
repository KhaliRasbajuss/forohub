package com.alura.forohub.forohub.curso;

import jakarta.validation.constraints.NotBlank;


public record DatosRegistroCurso(
    @NotBlank
    String nombre,
    Categoria categoria
) {

}
