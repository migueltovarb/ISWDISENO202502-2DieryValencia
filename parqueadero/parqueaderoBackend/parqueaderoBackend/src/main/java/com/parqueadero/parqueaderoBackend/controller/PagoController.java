package com.parqueadero.parqueaderoBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.model.Pago;
import com.parqueadero.parqueaderoBackend.service.PagoService;
import com.parqueadero.parqueaderoBackend.service.ReservaService;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;
    private final ReservaService reservaService;

    public PagoController(PagoService pagoService, ReservaService reservaService) {
        this.pagoService = pagoService;
        this.reservaService = reservaService;
    }

    @PostMapping("/procesar")
    public ResponseEntity<Pago> procesarPago(@RequestBody Pago pago) {
        Pago processedPago = pagoService.processPago(pago.getReservaId(), pago.getMonto(), pago.getMedioPago());
        return ResponseEntity.ok(processedPago);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPago(@PathVariable String id) {
        Pago pago = pagoService.findById(id).orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        return ResponseEntity.ok(pago);
    }
}