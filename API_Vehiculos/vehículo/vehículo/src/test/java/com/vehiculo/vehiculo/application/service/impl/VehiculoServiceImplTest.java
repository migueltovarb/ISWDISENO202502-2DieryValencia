package com.vehiculo.vehiculo.application.service.impl;

import com.vehiculo.vehiculo.application.dto.CrearVehiculoDTO;
import com.vehiculo.vehiculo.application.dto.VehiculoDTO;
import com.vehiculo.vehiculo.application.mapper.VehiculoMapper;
import com.vehiculo.vehiculo.domain.entity.Vehiculo;
import com.vehiculo.vehiculo.domain.repository.VehiculoRepository;
import com.vehiculo.vehiculo.infrastructure.exception.VehiculoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceImplTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private VehiculoMapper vehiculoMapper;

    @InjectMocks
    private VehiculoServiceImpl vehiculoService;

    private CrearVehiculoDTO crearVehiculoDTO;
    private Vehiculo vehiculo;
    private VehiculoDTO vehiculoDTO;

    @BeforeEach
    void setUp() {
        crearVehiculoDTO = CrearVehiculoDTO.builder()
                .placa("ABC123")
                .marca("Toyota")
                .modelo("Corolla")
                .año(2023)
                .color("Blanco")
                .tipo("Auto")
                .precio(25000.0)
                .estado("Disponible")
                .build();

        vehiculo = Vehiculo.builder()
                .id("507f1f77bcf86cd799439011")
                .placa("ABC123")
                .marca("Toyota")
                .modelo("Corolla")
                .año(2023)
                .color("Blanco")
                .tipo("Auto")
                .precio(25000.0)
                .estado("Disponible")
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        vehiculoDTO = VehiculoDTO.builder()
                .id("507f1f77bcf86cd799439011")
                .placa("ABC123")
                .marca("Toyota")
                .modelo("Corolla")
                .año(2023)
                .color("Blanco")
                .tipo("Auto")
                .precio(25000.0)
                .estado("Disponible")
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();
    }

    @Test
    void crearVehiculo_Exitoso() {
        when(vehiculoMapper.toEntity(crearVehiculoDTO)).thenReturn(vehiculo);
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(Mono.just(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        StepVerifier.create(vehiculoService.crearVehiculo(crearVehiculoDTO))
                .expectNext(vehiculoDTO)
                .verifyComplete();

        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    void obtenerVehiculoPorId_Exitoso() {
        when(vehiculoRepository.findById("507f1f77bcf86cd799439011"))
                .thenReturn(Mono.just(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        StepVerifier.create(vehiculoService.obtenerVehiculoPorId("507f1f77bcf86cd799439011"))
                .expectNext(vehiculoDTO)
                .verifyComplete();
    }

    @Test
    void obtenerVehiculoPorId_NoEncontrado() {
        when(vehiculoRepository.findById(anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(vehiculoService.obtenerVehiculoPorId("id_inexistente"))
                .expectError(VehiculoNoEncontradoException.class)
                .verify();
    }

    @Test
    void obtenerTodosLosVehiculos() {
        when(vehiculoRepository.findAll())
                .thenReturn(Flux.just(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        StepVerifier.create(vehiculoService.obtenerTodosLosVehiculos())
                .expectNext(vehiculoDTO)
                .verifyComplete();
    }

    @Test
    void actualizarVehiculo_Exitoso() {
        when(vehiculoRepository.findById("507f1f77bcf86cd799439011"))
                .thenReturn(Mono.just(vehiculo));
        when(vehiculoRepository.save(any(Vehiculo.class)))
                .thenReturn(Mono.just(vehiculo));
        when(vehiculoMapper.toDTO(vehiculo)).thenReturn(vehiculoDTO);

        StepVerifier.create(vehiculoService.actualizarVehiculo("507f1f77bcf86cd799439011", crearVehiculoDTO))
                .expectNext(vehiculoDTO)
                .verifyComplete();

        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    void eliminarVehiculo_Exitoso() {
        when(vehiculoRepository.findById("507f1f77bcf86cd799439011"))
                .thenReturn(Mono.just(vehiculo));
        when(vehiculoRepository.deleteById("507f1f77bcf86cd799439011"))
                .thenReturn(Mono.empty());

        StepVerifier.create(vehiculoService.eliminarVehiculo("507f1f77bcf86cd799439011"))
                .verifyComplete();

        verify(vehiculoRepository, times(1)).deleteById("507f1f77bcf86cd799439011");
    }
}
