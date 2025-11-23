package com.parqueadero.parqueaderoBackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.parqueaderoBackend.model.Cupo;

@Repository
public interface CupoRepository extends MongoRepository<Cupo, String> {

    public List<Cupo> findByTipo(String tipo);
    List<Cupo> findByEstado(String estado);   
    List<Cupo> findByTipoAndEstado(String tipo, String estado);
}