package com.parqueadero.parqueaderoBackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cupos")
public class Cupo {
    @Id
    private String id;
    private String numero; // e.g. "A1", "B2"
    private String tipo; // e.g. "CARRO", "MOTO"
    private double precio; // precio por hora
    private EstadoCupo estado; // DISPONIBLE, OCUPADO
    @Version
    private Long version;

    public Cupo() {}

    public Cupo(String id, String numero, String tipo, double precio, EstadoCupo estado, Long version) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.estado = estado;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoCupo getEstado() {
        return estado;
    }

    public void setEstado(EstadoCupo estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Cupo{" +
                "id='" + id + '\'' +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                ", estado=" + estado +
                ", version=" + version +
                '}';
    }
}