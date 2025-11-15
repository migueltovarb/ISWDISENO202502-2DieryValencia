package com.vehiculo.vehiculo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearVehiculoDTO {

    private String placa;

    private String marca;

    private String modelo;

    private Integer año;

    private String color;

    private String tipo;

    private Double precio;

    private String estado;
}
