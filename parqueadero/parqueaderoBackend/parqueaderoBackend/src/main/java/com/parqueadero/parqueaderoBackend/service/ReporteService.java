package com.parqueadero.parqueaderoBackend.service;

import com.parqueadero.parqueaderoBackend.model.Pago;
import com.parqueadero.parqueaderoBackend.model.Reserva;
import com.parqueadero.parqueaderoBackend.repository.PagoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PagoRepository pagoRepository;

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
    public double getTotalFacturacion(LocalDate inicio, LocalDate fin) {
        return pagoRepository.findAll().stream()
                .filter(p -> !p.getFecha().toLocalDate().isBefore(inicio) && !p.getFecha().toLocalDate().isAfter(fin))
                .mapToDouble(Pago::getMonto)
                .sum();
    }

    // Más reportes pueden agregarse aquí
}