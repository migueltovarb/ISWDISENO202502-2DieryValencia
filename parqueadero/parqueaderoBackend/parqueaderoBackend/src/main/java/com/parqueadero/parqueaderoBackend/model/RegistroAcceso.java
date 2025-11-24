package com.parqueadero.parqueaderoBackend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registros_acceso")
public class RegistroAcceso {
    @Id
    private String id;
    private String reservaId;
    private String qrId;
    private TipoAcceso tipoAcceso;
    private LocalDateTime timestamp;
    private boolean exitoso;
    private String motivoRechazo;

    @Version
    private Long version;

    public RegistroAcceso() {}

    public RegistroAcceso(String id, String reservaId, String qrId, TipoAcceso tipoAcceso, LocalDateTime timestamp, boolean exitoso, String motivoRechazo, Long version) {
        this.id = id;
        this.reservaId = reservaId;
        this.qrId = qrId;
        this.tipoAcceso = tipoAcceso;
        this.timestamp = timestamp;
        this.exitoso = exitoso;
        this.motivoRechazo = motivoRechazo;
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

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }

    public TipoAcceso getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(TipoAcceso tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "RegistroAcceso{" +
                "id='" + id + '\'' +
                ", reservaId='" + reservaId + '\'' +
                ", qrId='" + qrId + '\'' +
                ", tipoAcceso=" + tipoAcceso +
                ", timestamp=" + timestamp +
                ", exitoso=" + exitoso +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                ", version=" + version +
                '}';
    }
}