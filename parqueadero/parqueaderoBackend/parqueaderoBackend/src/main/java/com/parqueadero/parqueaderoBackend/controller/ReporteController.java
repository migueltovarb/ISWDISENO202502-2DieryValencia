package com.parqueadero.parqueaderoBackend.controller;

import java.time.LocalDate;
import java.util.List;

import org.bson.Document;
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
    public ResponseEntity<List<Document>> generarReporteOcupacion(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Document> reporte = reporteService.getOcupacionPorDia(fecha);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/facturacion")
    public ResponseEntity<Double> generarReporteFacturacion(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        double total = reporteService.getTotalFacturacion(fechaInicio, fechaFin);
        return ResponseEntity.ok(total);
    }
}