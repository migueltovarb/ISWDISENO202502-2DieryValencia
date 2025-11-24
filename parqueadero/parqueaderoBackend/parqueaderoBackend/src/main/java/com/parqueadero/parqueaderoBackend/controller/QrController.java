package com.parqueadero.parqueaderoBackend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.model.QR;
import com.parqueadero.parqueaderoBackend.service.QrService;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    private final QrService qrService;

    public QrController(QrService qrService) {
        this.qrService = qrService;
    }

    @PostMapping("/generar/{reservaId}")
    public ResponseEntity<QR> generarQR(@PathVariable String reservaId) {
        try {
            QR qr = qrService.generarQR(reservaId);
            return ResponseEntity.status(201).body(qr);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<QR> obtenerQR(@PathVariable String id) {
        QR qr = qrService.findById(id).orElse(null);
        if (qr == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(qr);
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<QR> obtenerQRReserva(@PathVariable String reservaId) {
        QR qr = qrService.findByReservaId(reservaId).orElse(null);
        if (qr == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(qr);
    }

    @PostMapping("/validar")
    public ResponseEntity<String> validarQR(String codigoQR) {
        boolean valido = qrService.validarQR(codigoQR);
        if (valido) {
            return ResponseEntity.ok("QR válido");
        } else {
            return ResponseEntity.badRequest().body("QR inválido");
        }
    }

    @PatchMapping("/{id}/invalidar")
    public ResponseEntity<QR> invalidarQR(@PathVariable String id) {
        try {
            QR qr = qrService.invalidarQR(id);
            return ResponseEntity.ok(qr);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/generate/{reservaId}")
    public ResponseEntity<byte[]> generarImagenQr(@PathVariable String reservaId) throws Exception {
        QR qr = qrService.findByReservaId(reservaId).orElseThrow(() -> new RuntimeException("QR no encontrado"));
        byte[] qrImage = qrService.generateQrImage(qr.getCodigoQR());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
    }
}