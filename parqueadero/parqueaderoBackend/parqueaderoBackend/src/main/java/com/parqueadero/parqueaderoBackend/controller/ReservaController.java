package com.parqueadero.parqueaderoBackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.Reserva;
import com.parqueadero.parqueaderoBackend.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        System.out.println("===== CREANDO RESERVA =====");
        System.out.println("Reserva recibida: " + reserva);
        System.out.println("Usuario ID: " + reserva.getUsuarioId());
        System.out.println("Cupo ID: " + reserva.getCupoId());
        System.out.println("Fecha inicio: " + reserva.getFechaInicio());
        System.out.println("Fecha fin: " + reserva.getFechaFin());

        try {
            Reserva nuevaReserva = reservaService.crearReserva(reserva);
            System.out.println("✅ Reserva creada: " + nuevaReserva.getId());
            return ResponseEntity.status(201).body(nuevaReserva);
        } catch (RuntimeException e) {
            System.out.println("❌ ERROR al crear reserva: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodasReservas() {
        List<Reserva> reservas = reservaService.findAll();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable String id) {
        Reserva reserva = reservaService.findById(id).orElse(null);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> obtenerReservasUsuario(@PathVariable String usuarioId) {
        List<Reserva> reservas = reservaService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/cupo/{cupoId}")
    public ResponseEntity<List<Reserva>> obtenerReservasCupo(@PathVariable String cupoId) {
        List<Reserva> reservas = reservaService.findByCupoId(cupoId);
        return ResponseEntity.ok(reservas);
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Reserva> confirmarReserva(@PathVariable String id) {
        try {
            Reserva reserva = reservaService.confirmarReserva(id);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable String id) {
        try {
            Reserva reserva = reservaService.cancelarReserva(id);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<Reserva> completarReserva(@PathVariable String id, @RequestBody Double costoFinal) {
        try {
            Reserva reserva = reservaService.completarReserva(id, costoFinal);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<Reserva> iniciarReserva(@PathVariable String id) {
        try {
            Reserva reserva = reservaService.cambiarEstado(id, EstadoReserva.EN_USO);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable String id, @RequestBody Reserva reserva) {
        try {
            reserva.setId(id);
            Reserva updated = reservaService.save(reserva);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable String id) {
        try {
            reservaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}