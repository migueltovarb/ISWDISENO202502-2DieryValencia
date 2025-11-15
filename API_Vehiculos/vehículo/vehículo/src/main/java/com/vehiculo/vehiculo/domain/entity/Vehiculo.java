package com.vehiculo.vehiculo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "vehiculos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehiculo {

    @Id
    private String id;

    private String placa;

    private String marca;

    private String modelo;

    private Integer año;

    private String color;

    private String tipo; // Ej: Auto, Moto, Camión

    private Double precio;

    private String estado; // Ej: Disponible, No disponible

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;
}
