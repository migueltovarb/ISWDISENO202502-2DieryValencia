package com.parqueadero.parqueaderoBackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reservas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    private String id;
    private String usuarioId;
    private String cupoId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double total;
    private EstadoReserva estado;
    private String qrToken; // unique token for QR
}