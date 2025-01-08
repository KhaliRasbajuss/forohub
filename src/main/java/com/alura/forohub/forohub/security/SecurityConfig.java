package com.alura.forohub.forohub.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alura.forohub.forohub.errors.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(crsf -> crsf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers( HttpMethod.POST,"/topico").hasAnyRole(Roles.ADMIN.name(), Roles.USUARIO.name());
                    req.requestMatchers("/usuario/*").permitAll();
                    req.requestMatchers("/topico/*").permitAll();
                    req.requestMatchers("/respuesta/*").permitAll();
                    req.requestMatchers("/topico/modificar/*", "/topico/eliminar/*").hasAnyRole(Roles.ADMIN.name(), Roles.USUARIO.name());
                    req.requestMatchers("/perfil", "/perfil/*").hasRole(Roles.ADMIN.name());
                    req.requestMatchers("/respuesta", "/respuesta/modificar/*", "/respuesta/eliminar/*").hasAnyRole(Roles.USUARIO.name(), Roles.ADMIN.name());
                    req.requestMatchers("/curso", "/curso/*").hasRole(Roles.USUARIO.name());
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                    req.anyRequest().authenticated();
                
                })
                .exceptionHandling(exceptions -> {
                    exceptions.authenticationEntryPoint(customAuthenticationEntryPoint);
                    exceptions.accessDeniedHandler(accessDeniedHandler());
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Map<String, Object> json = new HashMap<>();
            json.put("status", HttpStatus.UNAUTHORIZED.value());
            json.put("message", "No tienes permisos suficientes");
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(
                    objectMapper.writeValueAsString(json));
            response.getWriter().flush();
        };
    }
}