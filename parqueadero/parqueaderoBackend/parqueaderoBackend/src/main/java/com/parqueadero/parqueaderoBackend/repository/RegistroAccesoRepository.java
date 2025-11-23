package com.parqueadero.parqueaderoBackend.repository;

import com.parqueadero.parqueaderoBackend.model.EstadoRegistroAcceso;
import com.parqueadero.parqueaderoBackend.model.RegistroAcceso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistroAccesoRepository extends MongoRepository<RegistroAcceso, String> {
    Optional<RegistroAcceso> findByReservaIdAndEstado(String reservaId, EstadoRegistroAcceso estado);
}