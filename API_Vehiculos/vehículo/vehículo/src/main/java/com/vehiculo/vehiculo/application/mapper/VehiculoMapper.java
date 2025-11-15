package com.vehiculo.vehiculo.application.mapper;

import com.vehiculo.vehiculo.application.dto.CrearVehiculoDTO;
import com.vehiculo.vehiculo.application.dto.VehiculoDTO;
import com.vehiculo.vehiculo.domain.entity.Vehiculo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VehiculoMapper {

    public VehiculoDTO toDTO(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }
        return VehiculoDTO.builder()
                .id(vehiculo.getId())
                .placa(vehiculo.getPlaca())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .año(vehiculo.getAño())
                .color(vehiculo.getColor())
                .tipo(vehiculo.getTipo())
                .precio(vehiculo.getPrecio())
                .estado(vehiculo.getEstado())
                .fechaCreacion(vehiculo.getFechaCreacion())
                .fechaActualizacion(vehiculo.getFechaActualizacion())
                .build();
    }

    public Vehiculo toEntity(CrearVehiculoDTO dto) {
        if (dto == null) {
            return null;
        }
        LocalDateTime ahora = LocalDateTime.now();
        return Vehiculo.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .año(dto.getAño())
                .color(dto.getColor())
                .tipo(dto.getTipo())
                .precio(dto.getPrecio())
                .estado(dto.getEstado())
                .fechaCreacion(ahora)
                .fechaActualizacion(ahora)
                .build();
    }

    public void actualizarDatos(Vehiculo vehiculo, CrearVehiculoDTO dto) {
        if (dto == null) {
            return;
        }
        if (dto.getPlaca() != null) {
            vehiculo.setPlaca(dto.getPlaca());
        }
        if (dto.getMarca() != null) {
            vehiculo.setMarca(dto.getMarca());
        }
        if (dto.getModelo() != null) {
            vehiculo.setModelo(dto.getModelo());
        }
        if (dto.getAño() != null) {
            vehiculo.setAño(dto.getAño());
        }
        if (dto.getColor() != null) {
            vehiculo.setColor(dto.getColor());
        }
        if (dto.getTipo() != null) {
            vehiculo.setTipo(dto.getTipo());
        }
        if (dto.getPrecio() != null) {
            vehiculo.setPrecio(dto.getPrecio());
        }
        if (dto.getEstado() != null) {
            vehiculo.setEstado(dto.getEstado());
        }
        vehiculo.setFechaActualizacion(LocalDateTime.now());
    }
}
