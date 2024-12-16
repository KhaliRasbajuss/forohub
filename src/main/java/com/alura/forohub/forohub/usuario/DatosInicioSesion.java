package com.alura.forohub.forohub.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosInicioSesion(
    @Email
    @NotBlank
    String correoElectronico,
    @NotBlank
    String contraseña
) {

}
