package com.iplacex.discografia.discos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IDiscoRepository extends MongoRepository<Disco, String> {
    
    // Método personalizado exigido por la rúbrica
    @Query("{ 'idArtista': ?0 }")
    List<Disco> findDiscosByIdArtista(String idArtista);
}