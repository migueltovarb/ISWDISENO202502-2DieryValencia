package com.parqueadero.parqueaderoBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.dto.ReservaRequestDTO;
import com.parqueadero.parqueaderoBackend.dto.ReservaResponseDTO;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private com.parqueadero.parqueaderoBackend.service.ReservaService reservaService;
    private com.parqueadero.parqueaderoBackend.service.PagoService pagoService;

    public ReservaController() {}

    public ReservaController(com.parqueadero.parqueaderoBackend.service.ReservaService reservaService, com.parqueadero.parqueaderoBackend.service.PagoService pagoService) {
        this.reservaService = reservaService;
        this.pagoService = pagoService;
    }

    public com.parqueadero.parqueaderoBackend.service.ReservaService getReservaService() {
        return reservaService;
    }

    public void setReservaService(com.parqueadero.parqueaderoBackend.service.ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    public com.parqueadero.parqueaderoBackend.service.PagoService getPagoService() {
        return pagoService;
    }

    public void setPagoService(com.parqueadero.parqueaderoBackend.service.PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Override
    public String toString() {
        return "ReservaController{" +
                "reservaService=" + reservaService +
                ", pagoService=" + pagoService +
                '}';
    }

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
    public ResponseEntity<com.parqueadero.parqueaderoBackend.model.Pago> pagar(@PathVariable String id, @RequestParam Double monto, @RequestParam String medioPago) {
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