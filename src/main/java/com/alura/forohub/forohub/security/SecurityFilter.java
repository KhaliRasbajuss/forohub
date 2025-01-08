package com.alura.forohub.forohub.security;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.alura.forohub.forohub.errors.NotTokenValidException;
import com.alura.forohub.forohub.usuario.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final List<Pattern> RUTAS_EXCLUIDAS = List.of(
        Pattern.compile("^/usuario/.+$"),      
        Pattern.compile("^/respuesta/\\d+$"),
        Pattern.compile("^/topico/\\d+$"),    
        Pattern.compile("^/topico/todos$") ,
        Pattern.compile("^/respuesta/todos$"),
        Pattern.compile("^/v3/api-docs(/.*)?$"),
        Pattern.compile("^/swagger-ui\\.html$"),
        Pattern.compile("^/swagger-ui(/.*)?$")
    );


    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        for (Pattern pattern : RUTAS_EXCLUIDAS) {
            if (pattern.matcher(requestURI).matches()) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        if (token != null) {
            token = token.replace("Bearer ", "");
            System.out.println(token);
            var subject = tokenService.getSubject(token);
            if (subject != null) {
                var usuario = usuarioRepository.findByNombre(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                System.out.println(usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            throw new NotTokenValidException("No hay token");
        }
        filterChain.doFilter(request, response);
    }    
}