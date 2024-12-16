package com.alura.forohub.forohub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.forohub.forohub.errors.NotRequestBodyException;
import com.alura.forohub.forohub.usuario.DatosDetalleRegistro;
import com.alura.forohub.forohub.usuario.DatosInicioSesion;
import com.alura.forohub.forohub.usuario.DatosJWTToken;
import com.alura.forohub.forohub.usuario.DatosRegistrarUsuario;
import com.alura.forohub.forohub.usuario.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/register")
    public ResponseEntity<DatosDetalleRegistro> registrarUsuario(@RequestBody @Valid DatosRegistrarUsuario datos) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        var usuario = service.register(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }


    @PostMapping("/login")
    public ResponseEntity<DatosJWTToken> login(@RequestBody @Valid DatosInicioSesion datos) {
        if (datos == null) {
            throw new NotRequestBodyException("Necesito la Request Body");
        }
        var usuario = service.login(datos);
        return ResponseEntity.ok(new DatosJWTToken(usuario));
    }
}
