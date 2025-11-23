package com.parqueadero.parqueaderoBackend.repository;

import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {
    // Para verificar solapamiento: reservas activas que se solapen con el rango dado
    List<Reserva> findByCupoIdAndEstadoInAndFechaInicioLessThanAndFechaFinGreaterThan(
            String cupoId, List<EstadoReserva> estados, LocalDateTime fechaFin, LocalDateTime fechaInicio);

    Optional<Reserva> findByQrToken(String qrToken);
}