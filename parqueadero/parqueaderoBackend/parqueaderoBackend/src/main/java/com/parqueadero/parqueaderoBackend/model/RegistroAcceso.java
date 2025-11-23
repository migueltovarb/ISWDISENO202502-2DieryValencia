package com.parqueadero.parqueaderoBackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "registros_acceso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAcceso {
    @Id
    private String id;
    private String reservaId;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida; // nullable
    private EstadoRegistroAcceso estado;
}