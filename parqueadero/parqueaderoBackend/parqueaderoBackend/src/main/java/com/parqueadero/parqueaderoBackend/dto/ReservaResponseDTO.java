package com.parqueadero.parqueaderoBackend.dto;

import java.time.LocalDateTime;

import com.parqueadero.parqueaderoBackend.model.EstadoReserva;

public class ReservaResponseDTO {
    private String id;
    private String usuarioNombre;
    private String cupoNumero;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double total;
    private EstadoReserva estado;
    private String qrToken;

    public ReservaResponseDTO() {}

    public ReservaResponseDTO(String id, String usuarioNombre, String cupoNumero, LocalDateTime fechaInicio, LocalDateTime fechaFin, Double total, EstadoReserva estado, String qrToken) {
        this.id = id;
        this.usuarioNombre = usuarioNombre;
        this.cupoNumero = cupoNumero;
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

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getCupoNumero() {
        return cupoNumero;
    }

    public void setCupoNumero(String cupoNumero) {
        this.cupoNumero = cupoNumero;
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
        return "ReservaResponseDTO{" +
                "id='" + id + '\'' +
                ", usuarioNombre='" + usuarioNombre + '\'' +
                ", cupoNumero='" + cupoNumero + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", total=" + total +
                ", estado=" + estado +
                ", qrToken='" + qrToken + '\'' +
                '}';
    }
}