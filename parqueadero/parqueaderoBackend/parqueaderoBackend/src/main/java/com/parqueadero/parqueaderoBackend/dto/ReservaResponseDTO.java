package com.parqueadero.parqueaderoBackend.dto;

import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservaResponseDTO {
    private String id;
    private String usuarioNombre;
    private String cupoNumero;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double total;
    private EstadoReserva estado;
    private String qrToken;
}