package com.parqueadero.parqueaderoBackend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qrs")
public class QR {
    @Id
    private String id;
    private String reservaId;
    private String codigoQR;
    private String firmaHMAC;
    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaExpiracion;
    private EstadoQR estado;
    private String nonce;

    @Version
    private Long version;

    public QR() {}

    public QR(String id, String reservaId, String codigoQR, String firmaHMAC, LocalDateTime fechaGeneracion, LocalDateTime fechaExpiracion, EstadoQR estado, String nonce, Long version) {
        this.id = id;
        this.reservaId = reservaId;
        this.codigoQR = codigoQR;
        this.firmaHMAC = firmaHMAC;
        this.fechaGeneracion = fechaGeneracion;
        this.fechaExpiracion = fechaExpiracion;
        this.estado = estado;
        this.nonce = nonce;
        this.version = version;
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

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getFirmaHMAC() {
        return firmaHMAC;
    }

    public void setFirmaHMAC(String firmaHMAC) {
        this.firmaHMAC = firmaHMAC;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public EstadoQR getEstado() {
        return estado;
    }

    public void setEstado(EstadoQR estado) {
        this.estado = estado;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "QR{" +
                "id='" + id + '\'' +
                ", reservaId='" + reservaId + '\'' +
                ", codigoQR='" + codigoQR + '\'' +
                ", firmaHMAC='" + firmaHMAC + '\'' +
                ", fechaGeneracion=" + fechaGeneracion +
                ", fechaExpiracion=" + fechaExpiracion +
                ", estado=" + estado +
                ", nonce='" + nonce + '\'' +
                ", version=" + version +
                '}';
    }
}