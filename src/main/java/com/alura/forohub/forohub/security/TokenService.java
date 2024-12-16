package com.alura.forohub.forohub.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alura.forohub.forohub.errors.NotTokenValidException;
import com.alura.forohub.forohub.perfil.Perfil;
import com.alura.forohub.forohub.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;


@Service
public class TokenService {

    @Value("${api.security.jwt-secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            List<String> perfilesNombres = usuario.getPerfiles().stream()
                .map(Perfil::getNombre)
                .collect(Collectors.toList());
            return JWT.create()
                .withIssuer("ForoHub")
                .withSubject(usuario.getNombre())
                .withClaim("id", usuario.getId())
                .withClaim("perfiles", perfilesNombres)
                .withExpiresAt(generarFechaExpiracion())
                .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    @SuppressWarnings("null")    
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                .withIssuer("ForoHub")
                .build()
                .verify(token);

            verifier.getSubject();
        } catch (JWTVerificationException e) {
          throw new NotTokenValidException("Token invalido o verificable");
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifire Invalido");
        }
        return verifier.getSubject();
    }
    
    public Long getUsuarioId(String token) {
        if (token == null) {
            throw new IllegalArgumentException("El token no puede ser nulo");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("ForoHub")
                .build()
                .verify(token);
            return decodedJWT.getClaim("id").asLong();
        } catch (JWTVerificationException e) {
            throw new NotTokenValidException("Token invalido o verificable");
        }
    }
}
