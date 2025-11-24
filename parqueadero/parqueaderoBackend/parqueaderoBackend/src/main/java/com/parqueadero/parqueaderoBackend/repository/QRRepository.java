package com.parqueadero.parqueaderoBackend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.parqueaderoBackend.model.QR;

@Repository
public interface QRRepository extends MongoRepository<QR, String> {
    Optional<QR> findByReservaId(String reservaId);
    Optional<QR> findByCodigoQR(String codigoQR);
}