package com.vehiculo.vehiculo.infrastructure.exception;

/**
 * Excepción lanzada cuando se intenta crear un vehículo con una placa que ya existe.
 * Implementa la validación de unicidad de placas.
 */
public class PlacaDuplicadaException extends RuntimeException {
    
    public PlacaDuplicadaException(String mensaje) {
        super(mensaje);
    }
    
    public PlacaDuplicadaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
