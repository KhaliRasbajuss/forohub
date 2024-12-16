package com.alura.forohub.forohub.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.forohub.forohub.errors.AuthenticationException;
import com.alura.forohub.forohub.errors.PerfilException;
import com.alura.forohub.forohub.perfil.DatosDetallePerfil;
import com.alura.forohub.forohub.perfil.Perfil;
import com.alura.forohub.forohub.perfil.PerfilRepository;
import com.alura.forohub.forohub.security.TokenService;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PerfilRepository perfilRepository;
    

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public DatosDetalleRegistro register(DatosRegistrarUsuario datos) {
        Optional<Perfil> perfilOptional = perfilRepository.findByNombre(datos.perfiles().nombre().toUpperCase());
        List<Perfil> perfiles = new ArrayList<>();
        perfilOptional.ifPresentOrElse(perfiles::add, () -> {
            throw new PerfilException("Perfil '" + datos.perfiles().nombre() + "' no existe");
        });
        var usuario = new Usuario();
        usuario.setNombre(datos.nombre());
        usuario.setCorreoElectronico(datos.correoElectronico());
        usuario.setContraseña(passwordEncoder.encode(datos.contraseña()));
        usuario.setPerfiles(perfiles);
        var newUsuario = repository.save(usuario);
        List<DatosDetallePerfil> detallePerfil = DatosDetallePerfil.perfiles(perfiles);
        return new DatosDetalleRegistro(newUsuario.getId(), usuario.getNombre(), newUsuario.getCorreoElectronico(), detallePerfil);
    }

    public String login(DatosInicioSesion datos) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(datos.correoElectronico(), datos.contraseña());
            var usuarioAutenticado = authenticationManager.authenticate(authentication);
            var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            System.out.println(JWTToken);
            return JWTToken; 
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Usuario o contraseña incorrectos"); 
        } catch (Exception e) {
            throw new RuntimeException("Error en la autenticación", e);
        }
    }
}