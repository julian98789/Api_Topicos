package com.example.demo.infra.security;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.usuarios.Usuarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {
    @Value("${api.security.secret}")// Inyecta el valor de la propiedad 'api.security.secret' desde el archivo de configuración
    private String ApiSecreta ;

    public String generarToken(Usuarios usuarios) {
        try {
            // Crea el algoritmo HMAC256 con el secreto proporcionado
            Algorithm algorithm = Algorithm.HMAC256(ApiSecreta); // el tipo de algoritmo de encriptacion
            // Construye y firma el token JWT
            return JWT.create()
                    .withIssuer("api_topicos") // Establece el emisor del token
                    .withSubject(usuarios.getUsuario()) // Establece el sujeto del token como el login del usuario
                    .withClaim("id", usuarios.getId()) // Agrega un reclamo personalizado con el ID del usuario
                    .withExpiresAt(generarFechaDeexpiración()) // Establece la fecha de expiración del token
                    .sign(algorithm); // Firma el token con el algoritmo
        } catch (JWTCreationException exception) {
            // Maneja la excepción si ocurre un error durante la creación del token
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if (token == null){
            // Lanza una excepción si el token es nulo
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            // Crea el algoritmo HMAC256 con el secreto proporcionado para verificar el token
            Algorithm algorithm = Algorithm.HMAC256(ApiSecreta);
            // Verifica y decodifica el token
            verifier = JWT.require(algorithm)
                    .withIssuer("api_topicos") // Verifica el emisor del token
                    .build()
                    .verify(token); // Verifica el token
            verifier.getSubject(); // Obtiene el sujeto del token (login del usuario)

        } catch (JWTVerificationException exception) {
            // Maneja la excepción si ocurre un error durante la verificación del token
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null){
            // Lanza una excepción si el sujeto del token es nulo
            throw new RuntimeException("verifier invalido");
        }
        return verifier.getSubject(); // Retorna el sujeto del token
    }
    // Método privado para generar la fecha de expiración del token (3 horas desde ahora)
    private Instant generarFechaDeexpiración (){
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-05:00"));
    }
}
