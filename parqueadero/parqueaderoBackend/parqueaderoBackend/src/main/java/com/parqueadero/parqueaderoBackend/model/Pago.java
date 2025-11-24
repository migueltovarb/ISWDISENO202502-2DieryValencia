package com.parqueadero.parqueaderoBackend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pagos")
public class Pago {
    @Id
    private String id;
    private String reservaId;
    private Double monto;
    private MetodoPago metodoPago;
    private String tokenPago;
    private EstadoPago estado;
    private LocalDateTime fechaPago;

    @Version
    private Long version;

    public Pago() {}

    public Pago(String id, String reservaId, Double monto, MetodoPago metodoPago, String tokenPago, EstadoPago estado, LocalDateTime fechaPago, Long version) {
        this.id = id;
        this.reservaId = reservaId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.tokenPago = tokenPago;
        this.estado = estado;
        this.fechaPago = fechaPago;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getTokenPago() {
        return tokenPago;
    }

    public void setTokenPago(String tokenPago) {
        this.tokenPago = tokenPago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id='" + id + '\'' +
                ", reservaId='" + reservaId + '\'' +
                ", monto=" + monto +
                ", metodoPago=" + metodoPago +
                ", tokenPago='" + tokenPago + '\'' +
                ", estado=" + estado +
                ", fechaPago=" + fechaPago +
                ", version=" + version +
                '}';
    }
}