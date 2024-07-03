package com.example.demo.domain;


import java.time.LocalDateTime;

public record DatosRespuestaTopicos(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion
) {
}
