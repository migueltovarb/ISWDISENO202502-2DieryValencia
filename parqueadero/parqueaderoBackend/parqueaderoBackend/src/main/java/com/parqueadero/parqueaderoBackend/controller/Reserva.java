package com.parqueadero.parqueaderoBackend.controller;

import com.parqueadero.parqueaderoBackend.dto.ReservaRequestDTO;
import com.parqueadero.parqueaderoBackend.dto.ReservaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
public class Reserva {

    @Autowired
    private com.parqueadero.parqueaderoBackend.service.Reserva reservaService;

    @Autowired
    private com.parqueadero.parqueaderoBackend.service.Pago pagoService;

    @PostMapping
    public ResponseEntity<com.parqueadero.parqueaderoBackend.model.Reserva> createReserva(@RequestBody ReservaRequestDTO request) {
        try {
            com.parqueadero.parqueaderoBackend.model.Reserva reserva = reservaService.createReserva(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<com.parqueadero.parqueaderoBackend.model.Pago> pagar(@PathVariable String id, @RequestParam double monto, @RequestParam String medioPago) {
        try {
            com.parqueadero.parqueaderoBackend.model.Pago pago = pagoService.processPago(id, monto, medioPago);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> getReserva(@PathVariable String id) {
        try {
            ReservaResponseDTO response = reservaService.getReservaResponse(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}