package com.example.demo.domain;

import java.time.LocalDateTime;

public record ListaDatosTopicos (
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion
){
    public ListaDatosTopicos (Topicos topicos){
        this(topicos.getId(), topicos.getTitulo(), topicos.getMensaje(),topicos.getFechaCreacion());
    }
}
