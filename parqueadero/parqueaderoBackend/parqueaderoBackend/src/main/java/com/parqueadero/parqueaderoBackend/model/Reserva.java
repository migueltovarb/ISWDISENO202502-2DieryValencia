package com.parqueadero.parqueaderoBackend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservas")
public class Reserva {
    @Id
    private String id;
    private String usuarioId;
    private String cupoId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double total;
    private EstadoReserva estado;
    private String qrToken; // unique token for QR

    public Reserva() {}

    public Reserva(String id, String usuarioId, String cupoId, LocalDateTime fechaInicio, LocalDateTime fechaFin, Double total, EstadoReserva estado, String qrToken) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cupoId = cupoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
        this.estado = estado;
        this.qrToken = qrToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCupoId() {
        return cupoId;
    }

    public void setCupoId(String cupoId) {
        this.cupoId = cupoId;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id='" + id + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", cupoId='" + cupoId + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", total=" + total +
                ", estado=" + estado +
                ", qrToken='" + qrToken + '\'' +
                '}';
    }
}