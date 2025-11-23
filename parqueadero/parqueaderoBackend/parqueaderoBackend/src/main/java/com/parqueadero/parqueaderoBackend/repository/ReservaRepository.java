package com.parqueadero.parqueaderoBackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.Reserva;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {
    List<Reserva> findByUsuarioId(String usuarioId);
    List<Reserva> findByCupoId(String cupoId);
    List<Reserva> findByEstado(EstadoReserva estado);
    List<Reserva> findByUsuarioIdAndEstado(String usuarioId, EstadoReserva estado);
    List<Reserva> findByCupoIdAndEstadoIn(String cupoId, List<EstadoReserva> estados);
    Optional<Reserva> findByQrToken(String qrToken);
}