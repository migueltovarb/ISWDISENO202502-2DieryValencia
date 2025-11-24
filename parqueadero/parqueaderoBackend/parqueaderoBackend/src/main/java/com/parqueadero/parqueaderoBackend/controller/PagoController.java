package com.parqueadero.parqueaderoBackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.dto.PagoRequestDTO;
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



    @PostMapping
    public ResponseEntity<Pago> procesarPago(@RequestBody PagoRequestDTO request) {
            System.out.println("====== RECIBIENDO PAGO ======");
            System.out.println("Request recibido: " + request);
            System.out.println("============================");
            
        try {
            Pago processedPago = pagoService.processPago(
                request.getReservaId(), 
                request.getMonto(), 
                request.getMetodoPago()
            );
            System.out.println("PAGO PROCESADO EXITOSAMENTE: " + processedPago);
            return ResponseEntity.status(201).body(processedPago);
        } catch (RuntimeException e) {
            System.out.println("❌ ERROR AL PROCESAR PAGO: " + e.getMessage());
            e.printStackTrace(); // IMPORTANTE: Esto muestra el stack trace completo
            return ResponseEntity.badRequest().body(null);
        }
    }










   // @PostMapping
  //  public ResponseEntity<Pago> procesarPago(@RequestBody PagoRequestDTO request) {
  //      try {
  //          Pago processedPago = pagoService.processPago(request.getReservaId(), request.getMonto(), request.getMetodoPago());
  //          return ResponseEntity.status(201).body(processedPago);
  //      } catch (RuntimeException e) {
   //         return ResponseEntity.badRequest().body(null);
  //      }
 //   }

    @GetMapping
    public ResponseEntity<List<Pago>> obtenerTodosPagos() {
        List<Pago> pagos = pagoService.findAll();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPago(@PathVariable String id) {
        Pago pago = pagoService.findById(id).orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        return ResponseEntity.ok(pago);
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Pago>> obtenerPagosReserva(@PathVariable String reservaId) {
        List<Pago> pagos = pagoService.findByReservaId(reservaId);
        return ResponseEntity.ok(pagos);
    }

    @PatchMapping("/{id}/reintentar")
    public ResponseEntity<Pago> reintentarPago(@PathVariable String id) {
        try {
            Pago pago = pagoService.reintentarPago(id);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}