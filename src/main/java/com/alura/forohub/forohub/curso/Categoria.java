package com.alura.forohub.forohub.curso;

import com.alura.forohub.forohub.errors.CategoriaException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Categoria {
    BASEDEDATOS("BASE DE DATOS"),
    BIGDATA("BIG DATA"),
    BACKEND("BACKEND"),
    FRONTEND("FRONTEND"),
    SINCATEGORIA("SIN CATEGORIA");

    private final String value;

    Categoria(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Categoria fromValue(String value) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.value.equalsIgnoreCase(value)) {
                return categoria;
            }
        }
        throw new CategoriaException("Categoria desconocida:  " +  value);
    }
}
