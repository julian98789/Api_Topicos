package com.example.demo.domain.topicos;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroTopicos(

         @NotBlank
         String titulo,

         @NotBlank
         String mensaje

) {
}
