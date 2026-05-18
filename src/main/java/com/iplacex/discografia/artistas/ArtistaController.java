package com.iplacex.discografia.artistas;

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
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        try {
            Artista nuevoArtista = artistaRepository.save(artista);
            return new ResponseEntity<>(nuevoArtista, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el artista", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        List<Artista> artistas = artistaRepository.findAll();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isPresent()) {
            return new ResponseEntity<>(artista.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/artista/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
        artista._id = id; 
        Artista artistaActualizado = artistaRepository.save(artista);
        return new ResponseEntity<>(artistaActualizado, HttpStatus.OK);
    }

    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>("Artista no encontrado", HttpStatus.NOT_FOUND);
        }
        artistaRepository.deleteById(id);
        return new ResponseEntity<>("Artista eliminado", HttpStatus.OK);
    }
}