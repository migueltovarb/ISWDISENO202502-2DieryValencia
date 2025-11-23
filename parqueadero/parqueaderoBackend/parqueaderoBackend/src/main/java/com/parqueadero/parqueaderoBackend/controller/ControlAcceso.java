package com.parqueadero.parqueaderoBackend.controller;

import com.parqueadero.parqueaderoBackend.dto.EntradaRequestDTO;
import com.parqueadero.parqueaderoBackend.dto.SalidaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acceso")
public class ControlAcceso {

    @Autowired
    private com.parqueadero.parqueaderoBackend.service.ControlAcceso controlAcceso;

    @PostMapping("/entrada")
    public ResponseEntity<String> entrada(@RequestBody EntradaRequestDTO request) {
        try {
            String result = controlAcceso.validarEntrada(request.getQrToken());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/salida")
    public ResponseEntity<String> salida(@RequestBody SalidaRequestDTO request) {
        try {
            String result = controlAcceso.registrarSalida(request.getQrToken());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}