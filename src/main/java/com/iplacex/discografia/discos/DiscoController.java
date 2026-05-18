package com.iplacex.discografia.discos;

import com.iplacex.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        // Validación: Verifica si el artista existe por su ID
        if (disco.idArtista == null || !artistaRepository.existsById(disco.idArtista)) {
            return new ResponseEntity<>("Error: El artista asociado no existe", HttpStatus.NOT_FOUND);
        }
        
        try {
            Disco nuevoDisco = discoRepository.save(disco);
            return new ResponseEntity<>(nuevoDisco, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el disco", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> discos = discoRepository.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {
        Optional<Disco> disco = discoRepository.findById(id);
        if (disco.isPresent()) {
            return new ResponseEntity<>(disco.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Disco no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
        List<Disco> discos = discoRepository.findDiscosByIdArtista(idArtista);
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}