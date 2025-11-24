package com.parqueadero.parqueaderoBackend.dto;

import com.parqueadero.parqueaderoBackend.model.MetodoPago;

public class PagoRequestDTO {
    private String reservaId;
    private Double monto;
    private MetodoPago metodoPago;

    public PagoRequestDTO() {}

    public PagoRequestDTO(String reservaId, Double monto, MetodoPago metodoPago) {
        this.reservaId = reservaId;
        this.monto = monto;
        this.metodoPago = metodoPago;
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

    @Override
    public String toString() {
        return "PagoRequestDTO{" +
                "reservaId='" + reservaId + '\'' +
                ", monto=" + monto +
                ", metodoPago=" + metodoPago +
                '}';
    }
}