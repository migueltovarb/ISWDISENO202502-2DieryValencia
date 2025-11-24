package com.parqueadero.parqueaderoBackend.dto;

import lombok.Data;

@Data
public class SalidaRequestDTO {
    private String qrId;
    private String reservaId;
}