package com.parqueadero.parqueaderoBackend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.repository.PagoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;

@Service
public class ReporteService {

    private ReservaRepository reservaRepository;
    private PagoRepository pagoRepository;

    public ReporteService() {}

    public ReporteService(ReservaRepository reservaRepository, PagoRepository pagoRepository) {
        this.reservaRepository = reservaRepository;
        this.pagoRepository = pagoRepository;
    }

    public ReservaRepository getReservaRepository() {
        return reservaRepository;
    }

    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public PagoRepository getPagoRepository() {
        return pagoRepository;
    }

    public void setPagoRepository(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public String toString() {
        return "ReporteService{" +
                "reservaRepository=" + reservaRepository +
                ", pagoRepository=" + pagoRepository +
                '}';
    }

    // Reporte de ocupación por día (simplificado)
    public List<Document> getOcupacionPorDia(LocalDate fecha) {
        return reservaRepository.findAll().stream()
                .filter(r -> r.getFechaInicio().toLocalDate().equals(fecha))
                .map(r -> {
                    Document doc = new Document();
                    doc.put("cupoId", r.getCupoId());
                    doc.put("estado", r.getEstado().toString());
                    doc.put("usuarioId", r.getUsuarioId());
                    return doc;
                })
                .collect(Collectors.toList());
    }

    // Reporte de facturación total en un rango de fechas
    public Double getTotalFacturacion(LocalDate inicio, LocalDate fin) {
        return pagoRepository.findAll().stream()
                .filter(p -> p.getFechaPago() != null && !p.getFechaPago().toLocalDate().isBefore(inicio) && !p.getFechaPago().toLocalDate().isAfter(fin))
                .mapToDouble(p -> p.getMonto() != null ? p.getMonto() : 0.0)
                .sum();
    }

    // Más reportes pueden agregarse aquí
}