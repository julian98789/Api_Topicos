package com.example.demo.domain;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopicos(

         @NotBlank
         String titulo,

         @NotBlank
         String mensaje

) {
}
