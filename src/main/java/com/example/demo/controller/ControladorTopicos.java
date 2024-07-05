package com.example.demo.controller;

import com.example.demo.domain.topicos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/topicos")
@Tag(name = "Medico", description = "Controlador para la gestión de topicos")
public class ControladorTopicos {

    @Autowired
    private TopicosRepositorio topicosRepositorio;

    @PostMapping
    @Operation(summary = "Registrar un nuevo topico", description = "Registra un nuevo topico en la base de datos y devuelve la información del topico registrado.")
    public ResponseEntity<DatosRespuestaTopicos> resgistrarTopicos(
            @RequestBody @Valid DatosRegistroTopicos datosRegistroTopicos,
    UriComponentsBuilder uriComponentsBuilder){

        Topicos topicos = topicosRepositorio.save(new Topicos(datosRegistroTopicos));

        DatosRespuestaTopicos respuesta = new DatosRespuestaTopicos(
                topicos.getId(),
                topicos.getTitulo(),
                topicos.getMensaje(),
                topicos.getFechaCreacion()

        );

        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicos.getId()).toUri();

        return ResponseEntity.created(uri).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Obtener lista de topicos activos", description = "Devuelve una lista paginada de todos los topicos que están activos.")
    public ResponseEntity<Page<ListaDatosTopicos>> medicalList(
            @PageableDefault(size = 2) Pageable paginacion) {

        return ResponseEntity.ok(topicosRepositorio.findByActiveTrue(paginacion).map(ListaDatosTopicos::new));

    }

    @PutMapping // Define que este método maneja las solicitudes PUT a /medico
    @Transactional
    @Operation(summary = "Actualizar datos de un topico", description = "Actualiza la información de un topico existente en la base de datos.")
    public ResponseEntity<DatosRespuestaTopicos> actualizarTopicos(
            @RequestBody @Valid ActualizarDatosTopicos actualizarDatosTopicos){

        Topicos topicos = topicosRepositorio.getReferenceById(actualizarDatosTopicos.id());

        topicos.actualizarDatos(actualizarDatosTopicos);

        DatosRespuestaTopicos respuesta = new DatosRespuestaTopicos(
                topicos.getId(),
                topicos.getTitulo(),
                topicos.getMensaje(),
                topicos.getFechaCreacion()

        );
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}") // Define que este método maneja las solicitudes DELETE a /medico/{id}
    @Transactional
    @Operation(summary = "Desactivar un topico", description = "Desactiva lógicamente un topico por su ID.")
    public ResponseEntity<Void> eliminarTopicos(@PathVariable Long id) {

        Topicos topicos = topicosRepositorio.getReferenceById(id);

        topicos.deactivarTopico();


        return ResponseEntity.noContent().build();
    }

}
