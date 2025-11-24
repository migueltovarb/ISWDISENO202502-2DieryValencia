package com.parqueadero.parqueaderoBackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.parqueaderoBackend.model.Pago;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {
    List<Pago> findByReservaId(String reservaId);
}