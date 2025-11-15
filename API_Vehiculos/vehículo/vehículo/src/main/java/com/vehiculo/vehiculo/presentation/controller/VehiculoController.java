package com.vehiculo.vehiculo.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehiculo.vehiculo.application.dto.CrearVehiculoDTO;
import com.vehiculo.vehiculo.application.dto.VehiculoDTO;
import com.vehiculo.vehiculo.application.service.VehiculoService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    /**
     * Crear un nuevo vehículo
     * @param dto Datos del vehículo a crear
     * @return VehiculoDTO creado
     */
    @PostMapping
    public Mono<ResponseEntity<VehiculoDTO>> crearVehiculo(@RequestBody CrearVehiculoDTO dto) {
        return vehiculoService.crearVehiculo(dto)
                .map(vehiculo -> ResponseEntity.status(HttpStatus.CREATED).body(vehiculo));
    }

    /**
     * Obtener todos los vehículos
     * @return Lista de todos los vehículos
     */
    @GetMapping
    public Flux<VehiculoDTO> obtenerTodos() {
        return vehiculoService.obtenerTodosLosVehiculos();
    }

    /**
     * Obtener vehículo por ID
     * @param id ID del vehículo
     * @return VehiculoDTO encontrado
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<VehiculoDTO>> obtenerPorId(@PathVariable String id) {
        return vehiculoService.obtenerVehiculoPorId(id)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener vehículo por placa
     * @param placa Placa del vehículo
     * @return VehiculoDTO encontrado
     */
    @GetMapping("/placa/{placa}")
    public Mono<ResponseEntity<VehiculoDTO>> obtenerPorPlaca(@PathVariable String placa) {
        return vehiculoService.obtenerVehiculoPorPlaca(placa)
                .map(ResponseEntity::ok);
    }

    /**
     * Actualizar vehículo existente
     * @param id ID del vehículo a actualizar
     * @param dto Nuevos datos del vehículo
     * @return VehiculoDTO actualizado
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<VehiculoDTO>> actualizarVehiculo(
            @PathVariable String id,
            @RequestBody CrearVehiculoDTO dto) {
        return vehiculoService.actualizarVehiculo(id, dto)
                .map(ResponseEntity::ok);
    }

    /**
     * Eliminar vehículo
     * @param id ID del vehículo a eliminar
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarVehiculo(@PathVariable String id) {
        return vehiculoService.eliminarVehiculo(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
