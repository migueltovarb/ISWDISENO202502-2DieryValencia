package com.parqueadero.parqueaderoBackend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pagos")
public class Pago {
    @Id
    private String id;
    private String reservaId;
    private Double monto;
    private LocalDateTime fecha;
    private String medioPago; // e.g. "TARJETA", "EFECTIVO"

    public Pago() {}

    public Pago(String id, String reservaId, Double monto, LocalDateTime fecha, String medioPago) {
        this.id = id;
        this.reservaId = reservaId;
        this.monto = monto;
        this.fecha = fecha;
        this.medioPago = medioPago;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReservaId() {
        return reservaId;
    }

    public void setReservaId(String reservaId) {
        this.reservaId = reservaId;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id='" + id + '\'' +
                ", reservaId='" + reservaId + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", medioPago='" + medioPago + '\'' +
                '}';
    }
}