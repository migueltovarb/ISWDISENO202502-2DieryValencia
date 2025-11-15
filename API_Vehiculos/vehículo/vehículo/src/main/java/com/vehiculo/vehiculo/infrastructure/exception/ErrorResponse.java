package com.vehiculo.vehiculo.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String mensaje;
    private int codigo;
    private long timestamp;
}
