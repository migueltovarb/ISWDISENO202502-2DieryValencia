package com.parqueadero.parqueaderoBackend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservas")
public class Reserva {
    @Id
    private String id;
    private String usuarioId;
    private String cupoId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EstadoReserva estado;
    private Double costoEstimado;
    private Double costoFinal;
    private LocalDateTime fechaCreacion;
    private String qrToken;

    @Version
    private Long version;

    public Reserva() {}

    public Reserva(String id, String usuarioId, String cupoId, LocalDateTime fechaInicio, LocalDateTime fechaFin, EstadoReserva estado, Double costoEstimado, Double costoFinal, LocalDateTime fechaCreacion, String qrToken, Long version) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cupoId = cupoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.costoEstimado = costoEstimado;
        this.costoFinal = costoFinal;
        this.fechaCreacion = fechaCreacion;
        this.qrToken = qrToken;
        this.version = version;
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

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public Double getCostoFinal() {
        return costoFinal;
    }

    public void setCostoFinal(Double costoFinal) {
        this.costoFinal = costoFinal;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id='" + id + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", cupoId='" + cupoId + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado=" + estado +
                ", costoEstimado=" + costoEstimado +
                ", costoFinal=" + costoFinal +
                ", fechaCreacion=" + fechaCreacion +
                ", qrToken='" + qrToken + '\'' +
                ", version=" + version +
                '}';
    }
}