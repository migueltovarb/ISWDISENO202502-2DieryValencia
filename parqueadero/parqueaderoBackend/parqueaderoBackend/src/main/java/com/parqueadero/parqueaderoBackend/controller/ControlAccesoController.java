package com.parqueadero.parqueaderoBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.dto.EntradaRequestDTO;
import com.parqueadero.parqueaderoBackend.dto.SalidaRequestDTO;

@RestController
@RequestMapping("/api/acceso")
public class ControlAccesoController {

    private com.parqueadero.parqueaderoBackend.service.ControlAccesoService controlAccesoService;

    public ControlAccesoController() {}

    public ControlAccesoController(com.parqueadero.parqueaderoBackend.service.ControlAccesoService controlAccesoService) {
        this.controlAccesoService = controlAccesoService;
    }

    public com.parqueadero.parqueaderoBackend.service.ControlAccesoService getControlAccesoService() {
        return controlAccesoService;
    }

    public void setControlAccesoService(com.parqueadero.parqueaderoBackend.service.ControlAccesoService controlAccesoService) {
        this.controlAccesoService = controlAccesoService;
    }

    @Override
    public String toString() {
        return "ControlAccesoController{" +
                "controlAccesoService=" + controlAccesoService +
                '}';
    }

    @PostMapping("/entrada")
    public ResponseEntity<String> entrada(@RequestBody EntradaRequestDTO request) {
        try {
            String result = controlAccesoService.validarEntrada(request.getQrToken());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/salida")
    public ResponseEntity<String> salida(@RequestBody SalidaRequestDTO request) {
        try {
            String result = controlAccesoService.registrarSalida(request.getQrToken());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}