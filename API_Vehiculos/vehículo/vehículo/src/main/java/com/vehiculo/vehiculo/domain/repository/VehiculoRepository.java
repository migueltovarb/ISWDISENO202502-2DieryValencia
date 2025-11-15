package com.vehiculo.vehiculo.domain.repository;

import com.vehiculo.vehiculo.domain.entity.Vehiculo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VehiculoRepository extends ReactiveMongoRepository<Vehiculo, String> {
    Mono<Vehiculo> findByPlaca(String placa);
}
