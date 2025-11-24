package com.parqueadero.parqueaderoBackend.dto;

import lombok.Data;

@Data
public class EntradaRequestDTO {
    private String qrId;
    private String reservaId;
}