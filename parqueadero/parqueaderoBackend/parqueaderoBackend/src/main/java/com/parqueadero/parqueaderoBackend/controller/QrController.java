package com.parqueadero.parqueaderoBackend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.model.Reserva;
import com.parqueadero.parqueaderoBackend.service.QrService;
import com.parqueadero.parqueaderoBackend.service.ReservaService;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    private final QrService qrService;
    private final ReservaService reservaService;

    public QrController(QrService qrService, ReservaService reservaService) {
        this.qrService = qrService;
        this.reservaService = reservaService;
    }

    @GetMapping("/generate/{reservaId}")
    public ResponseEntity<byte[]> generarImagenQr(@PathVariable String reservaId) throws Exception {
        Reserva reserva = reservaService.findById(reservaId).orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        byte[] qrImage = qrService.generateQrImage(reserva.getQrToken());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
    }
}