package com.parqueadero.parqueaderoBackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "pagos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    private String id;
    private String reservaId;
    private double monto;
    private LocalDateTime fecha;
    private String medioPago; // e.g. "TARJETA", "EFECTIVO"
}