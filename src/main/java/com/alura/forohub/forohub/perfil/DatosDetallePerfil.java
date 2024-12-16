package com.alura.forohub.forohub.perfil;

import java.util.List;

public record DatosDetallePerfil(
Long id,
String nombre
) {
    public static List<DatosDetallePerfil> perfiles(List<Perfil> perfiles) {
        return perfiles.stream()
            .map(p-> new DatosDetallePerfil(p.getId(), p.getNombre()))
            .toList();
    }
}
