package com.parqueadero.parqueaderoBackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaRequestDTO {
    private String usuarioId;
    private String cupoId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}