package com.parqueadero.parqueaderoBackend.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.model.EstadoQR;
import com.parqueadero.parqueaderoBackend.model.QR;
import com.parqueadero.parqueaderoBackend.repository.QRRepository;

@Service
public class QrService {

    private static final String SECRET_KEY = "mi_clave_secreta_para_hmac"; // En producción usar variable de entorno
    private static final String HMAC_SHA256 = "HmacSHA256";

    private final QRRepository qrRepository;

    public QrService(QRRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    public QR generarQR(String reservaId) {
        String codigoQR = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiracion = now.plusHours(24); // Expira en 24 horas
        String nonce = UUID.randomUUID().toString();

        String firmaHMAC = generarFirmaHMAC(reservaId, expiracion, nonce);

        QR qr = new QR(
                null,
                reservaId,
                codigoQR,
                firmaHMAC,
                now,
                expiracion,
                EstadoQR.ACTIVO,
                nonce,
                null
        );

        return qrRepository.save(qr);
    }

    public boolean validarQR(String codigoQR) {
        System.out.println("===== VALIDANDO QR =====");
        System.out.println("Código QR recibido: " + codigoQR);

        Optional<QR> qrOpt = qrRepository.findByCodigoQR(codigoQR);

        if (qrOpt.isEmpty()) {
            System.out.println("❌ QR no encontrado en la base de datos");
            return false;
        }

        QR qr = qrOpt.get();
        System.out.println("✅ QR encontrado: " + qr.getId());
        System.out.println("Estado: " + qr.getEstado());
        System.out.println("Fecha expiración: " + qr.getFechaExpiracion());
        System.out.println("Fecha actual: " + LocalDateTime.now());

        // Verificar expiración
        if (LocalDateTime.now().isAfter(qr.getFechaExpiracion())) {
            System.out.println("❌ QR expirado");
            qr.setEstado(EstadoQR.EXPIRADO);
            qrRepository.save(qr);
            return false;
        }

        // Verificar estado (permitir ACTIVO o USADO)
        if (qr.getEstado() != EstadoQR.ACTIVO && qr.getEstado() != EstadoQR.USADO) {
            System.out.println("❌ Estado inválido: " + qr.getEstado());
            return false;
        }
        /* 

        // Verificar firma HMAC
        String firmaEsperada = generarFirmaHMAC(qr.getReservaId(), qr.getFechaExpiracion(), qr.getNonce());
        System.out.println("Firma esperada: " + firmaEsperada);
        System.out.println("Firma almacenada: " + qr.getFirmaHMAC());

        if (!firmaEsperada.equals(qr.getFirmaHMAC())) {
            System.out.println("❌ Firma HMAC inválida");
            return false;
        }
        */

        // Solo marcar como usado la primera vez
        if (qr.getEstado() == EstadoQR.ACTIVO) {
            System.out.println("✅ Marcando QR como USADO");
            qr.setEstado(EstadoQR.USADO);
            qrRepository.save(qr);
        }

        System.out.println("✅ QR válido");
        return true;
    }

    public Optional<QR> findById(String id) {
        return qrRepository.findById(id);
    }

    public Optional<QR> findByReservaId(String reservaId) {
        return qrRepository.findByReservaId(reservaId);
    }

    public QR invalidarQR(String id) {
        QR qr = qrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QR no encontrado"));
        qr.setEstado(EstadoQR.EXPIRADO);
        return qrRepository.save(qr);
    }

    private String generarFirmaHMAC(String reservaId, LocalDateTime expiracion, String nonce) {
        try {
            String data = reservaId + "|" + expiracion.toString() + "|" + nonce;
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKey);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error generando firma HMAC", e);
        }
    }

    public String generateQrToken() {
        return UUID.randomUUID().toString();
    }

    public byte[] generateQrImage(String text) throws Exception {
        // Placeholder - en implementación real usar ZXing
        return text.getBytes(StandardCharsets.UTF_8);
    }
}