package com.parqueadero.parqueaderoBackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.dto.EntradaRequestDTO;
import com.parqueadero.parqueaderoBackend.dto.SalidaRequestDTO;
import com.parqueadero.parqueaderoBackend.model.RegistroAcceso;
import com.parqueadero.parqueaderoBackend.service.ControlAccesoService;

@RestController
@RequestMapping("/api/acceso")
public class ControlAccesoController {

    private final ControlAccesoService controlAccesoService;

    public ControlAccesoController(ControlAccesoService controlAccesoService) {
        this.controlAccesoService = controlAccesoService;
    }

    @PostMapping("/entrada")
    public ResponseEntity<String> registrarEntrada(@RequestBody EntradaRequestDTO request) {
        try {
            String result = controlAccesoService.registrarEntrada(request.getQrId(), request.getReservaId());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PostMapping("/salida")
    public ResponseEntity<String> registrarSalida(@RequestBody SalidaRequestDTO request) {
        try {
            String result = controlAccesoService.registrarSalida(request.getQrId(), request.getReservaId());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<RegistroAcceso>> obtenerAccesosReserva(@PathVariable String reservaId) {
        List<RegistroAcceso> accesos = controlAccesoService.findByReservaId(reservaId);
        return ResponseEntity.ok(accesos);
    }

    @GetMapping("/cupo/{cupoId}")
    public ResponseEntity<List<RegistroAcceso>> obtenerAccesosCupo(@PathVariable String cupoId) {
        List<RegistroAcceso> accesos = controlAccesoService.findByCupoId(cupoId);
        return ResponseEntity.ok(accesos);
    }
}