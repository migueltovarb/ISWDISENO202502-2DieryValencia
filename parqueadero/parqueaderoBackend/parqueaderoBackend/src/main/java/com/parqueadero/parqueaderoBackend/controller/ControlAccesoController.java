package com.parqueadero.parqueaderoBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.service.ControlAccesoService;

@RestController
@RequestMapping("/api/acceso")
public class ControlAccesoController {

    private final ControlAccesoService controlAccesoService;

    public ControlAccesoController(ControlAccesoService controlAccesoService) {
        this.controlAccesoService = controlAccesoService;
    }

    @PostMapping("/entrada")
    public ResponseEntity<String> registrarEntrada(@RequestParam String token) {
        try {
            String result = controlAccesoService.validarEntrada(token);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PostMapping("/salida")
    public ResponseEntity<String> registrarSalida(@RequestParam String token) {
        try {
            String result = controlAccesoService.registrarSalida(token);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}