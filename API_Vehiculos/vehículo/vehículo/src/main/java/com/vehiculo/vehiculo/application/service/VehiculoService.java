package com.vehiculo.vehiculo.application.service;

import com.vehiculo.vehiculo.application.dto.CrearVehiculoDTO;
import com.vehiculo.vehiculo.application.dto.VehiculoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VehiculoService {
    
    Mono<VehiculoDTO> crearVehiculo(CrearVehiculoDTO dto);
    
    Mono<VehiculoDTO> obtenerVehiculoPorId(String id);
    
    Mono<VehiculoDTO> obtenerVehiculoPorPlaca(String placa);
    
    Flux<VehiculoDTO> obtenerTodosLosVehiculos();
    
    Mono<VehiculoDTO> actualizarVehiculo(String id, CrearVehiculoDTO dto);
    
    Mono<Void> eliminarVehiculo(String id);
}
