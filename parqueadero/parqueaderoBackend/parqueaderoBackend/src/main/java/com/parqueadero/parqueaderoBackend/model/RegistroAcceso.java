package com.parqueadero.parqueaderoBackend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registros_acceso")
public class RegistroAcceso {
    @Id
    private String id;
    private String reservaId;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida; // nullable
    private EstadoRegistroAcceso estado;

    public RegistroAcceso() {}

    public RegistroAcceso(String id, String reservaId, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, EstadoRegistroAcceso estado) {
        this.id = id;
        this.reservaId = reservaId;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
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

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public EstadoRegistroAcceso getEstado() {
        return estado;
    }

    public void setEstado(EstadoRegistroAcceso estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "RegistroAcceso{" +
                "id='" + id + '\'' +
                ", reservaId='" + reservaId + '\'' +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                ", estado=" + estado +
                '}';
    }
}