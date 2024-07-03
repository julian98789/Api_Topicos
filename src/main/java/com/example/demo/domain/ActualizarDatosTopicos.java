package com.example.demo.domain;

import jakarta.validation.constraints.NotNull;

public record ActualizarDatosTopicos(
        @NotNull
        Long id,
        String titulo,
        String mensaje
) {
}
