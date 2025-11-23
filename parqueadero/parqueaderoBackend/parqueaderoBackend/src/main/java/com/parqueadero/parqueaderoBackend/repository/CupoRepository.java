package com.parqueadero.parqueaderoBackend.repository;

import com.parqueadero.parqueaderoBackend.model.Cupo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupoRepository extends MongoRepository<Cupo, String> {
}