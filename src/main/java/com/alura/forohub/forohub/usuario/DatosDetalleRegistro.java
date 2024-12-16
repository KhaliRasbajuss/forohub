package com.alura.forohub.forohub.usuario;


import java.util.List;

import com.alura.forohub.forohub.perfil.DatosDetallePerfil;


public record DatosDetalleRegistro(
Long id,
String nombre,
String email,
List<DatosDetallePerfil> perfiles
) {

}
