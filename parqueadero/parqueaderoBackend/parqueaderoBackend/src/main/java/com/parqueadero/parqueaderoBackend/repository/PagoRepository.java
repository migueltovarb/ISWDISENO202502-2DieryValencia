package com.parqueadero.parqueaderoBackend.repository;

import com.parqueadero.parqueaderoBackend.model.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {
}