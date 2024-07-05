package com.example.demo.controller;

import com.example.demo.domain.usuarios.DatosAutenticacionUsuarios;
import com.example.demo.domain.usuarios.Usuarios;
import com.example.demo.infra.security.DatosJwtToken;
import com.example.demo.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/login") // Define la ruta base para este controlador
@Tag(name = "Autenticacion", description = "Obtiene el token para el usuario correspondiente que permite el acceso a los demás endpoints.")
public class ControladorAutentificacion {

    @Autowired // Inyecta automáticamente una instancia de AuthenticationManager
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Autenticar usuario", description = "Autentica al usuario con las credenciales proporcionadas y genera un token JWT.")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarios datosAutenticacionUsuarios) {

        Authentication autenticarToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuarios.usuario(), datosAutenticacionUsuarios.contraseña());

        var usuarioAutenticado = authenticationManager.authenticate(autenticarToken);

        var JWTtoken= tokenService.generarToken((Usuarios) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJwtToken(JWTtoken));


    }
}
