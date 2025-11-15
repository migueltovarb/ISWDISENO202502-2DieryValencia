package com.vehiculo.vehiculo.infrastructure.exception;

public class VehiculoNoEncontradoException extends RuntimeException {
    public VehiculoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
