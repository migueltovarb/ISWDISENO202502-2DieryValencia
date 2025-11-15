package com.vehiculo.vehiculo.application.service.impl;

import com.vehiculo.vehiculo.application.dto.CrearVehiculoDTO;
import com.vehiculo.vehiculo.application.dto.VehiculoDTO;
import com.vehiculo.vehiculo.application.mapper.VehiculoMapper;
import com.vehiculo.vehiculo.application.service.VehiculoService;
import com.vehiculo.vehiculo.domain.entity.Vehiculo;
import com.vehiculo.vehiculo.domain.repository.VehiculoRepository;
import com.vehiculo.vehiculo.infrastructure.exception.VehiculoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    @Override
    public Mono<VehiculoDTO> crearVehiculo(CrearVehiculoDTO dto) {
        Vehiculo vehiculo = vehiculoMapper.toEntity(dto);
        return vehiculoRepository.save(vehiculo)
                .map(vehiculoMapper::toDTO);
    }

    @Override
    public Mono<VehiculoDTO> obtenerVehiculoPorId(String id) {
        return vehiculoRepository.findById(id)
                .map(vehiculoMapper::toDTO)
                .switchIfEmpty(Mono.error(
                        new VehiculoNoEncontradoException("Vehículo con ID: " + id + " no encontrado")
                ));
    }

    @Override
    public Mono<VehiculoDTO> obtenerVehiculoPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa)
                .map(vehiculoMapper::toDTO)
                .switchIfEmpty(Mono.error(
                        new VehiculoNoEncontradoException("Vehículo con placa: " + placa + " no encontrado")
                ));
    }

    @Override
    public Flux<VehiculoDTO> obtenerTodosLosVehiculos() {
        return vehiculoRepository.findAll()
                .map(vehiculoMapper::toDTO);
    }

    @Override
    public Mono<VehiculoDTO> actualizarVehiculo(String id, CrearVehiculoDTO dto) {
        return vehiculoRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new VehiculoNoEncontradoException("Vehículo con ID: " + id + " no encontrado")
                ))
                .flatMap(vehiculo -> {
                    vehiculoMapper.actualizarDatos(vehiculo, dto);
                    return vehiculoRepository.save(vehiculo);
                })
                .map(vehiculoMapper::toDTO);
    }

    @Override
    public Mono<Void> eliminarVehiculo(String id) {
        return vehiculoRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new VehiculoNoEncontradoException("Vehículo con ID: " + id + " no encontrado")
                ))
                .flatMap(vehiculo -> vehiculoRepository.deleteById(id));
    }
}
