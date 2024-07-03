package com.example.demo.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos") // Define la tabla en la base de datos que corresponde a esta entidad
@Entity(name = "topico") // Define el nombre de la entidad en JPA
@Getter // Genera automáticamente getters para todos los campos utilizando Lombok
@NoArgsConstructor // Genera automáticamente un constructor sin argumentos utilizando Lombok
@AllArgsConstructor // Genera automáticamente un constructor con todos los argumentos utilizando Lombok
@EqualsAndHashCode(of = "id")
public class Topicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el campo "id" es una clave primaria generada automáticamente
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Boolean active;

    public Topicos(DatosRegistroTopicos datosRegistroTopicos) {
        this.active = true;
        this.titulo= datosRegistroTopicos.titulo();
        this.mensaje= datosRegistroTopicos.mensaje();
        this.fechaCreacion=  LocalDateTime.now();
    }

    public void actualizarDatos(ActualizarDatosTopicos actualizarDatosTopicos) {
        if (actualizarDatosTopicos.titulo() != null) {
            this.titulo = actualizarDatosTopicos.titulo();
        }
        if (actualizarDatosTopicos.mensaje() != null) {
            this.mensaje = actualizarDatosTopicos.mensaje();
        }
    }

    // Método para desactivar un médico
    public void deactivarTopico() {
        this.active = false;
    }
}
