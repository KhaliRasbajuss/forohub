package com.alura.forohub.forohub.usuario;


import com.alura.forohub.forohub.perfil.DatosRegistrarPerfil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistrarUsuario(
    @NotBlank
    String nombre,
    @Email
    String correoElectronico,
    @NotBlank
    String contraseña,
    @Valid
    @NotNull
    DatosRegistrarPerfil perfiles
) {

}
