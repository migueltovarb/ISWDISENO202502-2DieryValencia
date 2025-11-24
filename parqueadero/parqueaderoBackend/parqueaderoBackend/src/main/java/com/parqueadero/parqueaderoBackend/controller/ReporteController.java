package com.parqueadero.parqueaderoBackend.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/ocupacion")
    public ResponseEntity<Map<String, Object>> generarReporteOcupacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) String zona) {

        Map<String, Object> reporte = new HashMap<>();
        reporte.put("periodo", Map.of("inicio", fechaInicio, "fin", fechaFin));
        reporte.put("tasaOcupacion", 78.5); // Mock data
        reporte.put("totalReservas", 150);
        reporte.put("tiempoPromedioOcupacion", 3.5);
        reporte.put("cuposMasUsados", new String[]{"A1", "B2", "C3"});
        reporte.put("horariosPico", new String[]{"08:00-10:00", "17:00-19:00"});

        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/facturacion")
    public ResponseEntity<Map<String, Object>> generarReporteFacturacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) String metodoPago) {

        Double total = reporteService.getTotalFacturacion(fechaInicio, fechaFin);

        Map<String, Object> reporte = new HashMap<>();
        reporte.put("periodo", Map.of("inicio", fechaInicio, "fin", fechaFin));
        reporte.put("ingresosTotales", total);
        reporte.put("totalTransacciones", 120);
        reporte.put("ticketPromedio", total / 120);
        reporte.put("desglosePorMetodo", Map.of("TARJETA", 80, "BILLETERA_DIGITAL", 40));
        reporte.put("transaccionesFallidas", 5);

        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> generarEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalUsuarios", 500);
        estadisticas.put("totalReservas", 1200);
        estadisticas.put("totalCupos", 50);
        estadisticas.put("ocupacionPromedio", 75.0);
        estadisticas.put("ingresosMesActual", 2500000.0);

        return ResponseEntity.ok(estadisticas);
    }
}