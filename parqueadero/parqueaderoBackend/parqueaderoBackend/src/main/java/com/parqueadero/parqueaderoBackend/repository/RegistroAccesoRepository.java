package com.parqueadero.parqueaderoBackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.parqueaderoBackend.model.RegistroAcceso;

@Repository
public interface RegistroAccesoRepository extends MongoRepository<RegistroAcceso, String> {
}