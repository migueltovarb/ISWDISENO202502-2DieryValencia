package com.vehiculo.vehiculo.application.service.impl;

import com.vehiculo.vehiculo.application.dto.CrearVehiculoDTO;
import com.vehiculo.vehiculo.application.dto.VehiculoDTO;
import com.vehiculo.vehiculo.application.mapper.VehiculoMapper;
import com.vehiculo.vehiculo.application.service.VehiculoService;
import com.vehiculo.vehiculo.domain.entity.Vehiculo;
import com.vehiculo.vehiculo.domain.repository.VehiculoRepository;
import com.vehiculo.vehiculo.infrastructure.exception.PlacaDuplicadaException;
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

    /**
     * Crea un nuevo vehículo validando que la placa sea única.
     * @param dto Datos del vehículo a crear
     * @return Mono con el vehículo creado
     * @throws PlacaDuplicadaException si la placa ya existe
     */
    @Override
    public Mono<VehiculoDTO> crearVehiculo(CrearVehiculoDTO dto) {
        return vehiculoRepository.findByPlaca(dto.getPlaca())
                .hasElement()
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(
                                new PlacaDuplicadaException("La placa '" + dto.getPlaca() + "' ya existe. No se permite duplicar placas.")
                        );
                    }
                    Vehiculo vehiculo = vehiculoMapper.toEntity(dto);
                    return vehiculoRepository.save(vehiculo)
                            .map(vehiculoMapper::toDTO);
                });
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

    /**
     * Actualiza un vehículo existente validando que la placa sea única.
     * @param id ID del vehículo a actualizar
     * @param dto Nuevos datos del vehículo
     * @return Mono con el vehículo actualizado
     * @throws VehiculoNoEncontradoException si el vehículo no existe
     * @throws PlacaDuplicadaException si la nueva placa ya existe en otro vehículo
     */
    @Override
    public Mono<VehiculoDTO> actualizarVehiculo(String id, CrearVehiculoDTO dto) {
        return vehiculoRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new VehiculoNoEncontradoException("Vehículo con ID: " + id + " no encontrado")
                ))
                .flatMap(vehiculo -> {
                    // Si la placa cambió, validar que no exista otra con esa placa
                    if (!vehiculo.getPlaca().equals(dto.getPlaca())) {
                        return vehiculoRepository.findByPlaca(dto.getPlaca())
                                .hasElement()
                                .flatMap(existe -> {
                                    if (existe) {
                                        return Mono.error(
                                                new PlacaDuplicadaException("La placa '" + dto.getPlaca() + "' ya existe. No se permite duplicar placas.")
                                        );
                                    }
                                    vehiculoMapper.actualizarDatos(vehiculo, dto);
                                    return vehiculoRepository.save(vehiculo);
                                });
                    }
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
