package com.parqueadero.parqueaderoBackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.model.EstadoPago;
import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.MetodoPago;
import com.parqueadero.parqueaderoBackend.model.Pago;
import com.parqueadero.parqueaderoBackend.model.QR;
import com.parqueadero.parqueaderoBackend.model.Reserva;
import com.parqueadero.parqueaderoBackend.repository.PagoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;

@Service
public class PagoService {

    private PagoRepository pagoRepository;
    private ReservaRepository reservaRepository;
    private QrService qrService;

    public PagoService(PagoRepository pagoRepository, ReservaRepository reservaRepository, QrService qrService) {
        this.pagoRepository = pagoRepository;
        this.reservaRepository = reservaRepository;
        this.qrService = qrService;
    }

    public PagoRepository getPagoRepository() {
        return pagoRepository;
    }

    public void setPagoRepository(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public ReservaRepository getReservaRepository() {
        return reservaRepository;
    }

    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public QrService getQrService() {
        return qrService;
    }

    public void setQrService(QrService qrService) {
        this.qrService = qrService;
    }

    public String toString() {
        return "PagoService{" +
                "pagoRepository=" + pagoRepository +
                ", reservaRepository=" + reservaRepository +
                ", qrService=" + qrService +
                '}';
    }

  public Pago processPago(String reservaId, Double monto, MetodoPago metodoPago) {
    Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

    if (reserva.getEstado() != EstadoReserva.PENDIENTE_PAGO) {
        throw new RuntimeException("Reserva no está pendiente de pago");
    }

    // Assume payment is successful
    Pago pago = new Pago(
            null,
            reservaId,
            monto,
            metodoPago,
            "token_" + System.currentTimeMillis(),
            EstadoPago.COMPLETADO,
            LocalDateTime.now(),
            null
    );

    pagoRepository.save(pago);

    // ✅ GENERAR QR COMPLETO (en lugar de solo el token)
    QR qrGenerado = qrService.generarQR(reservaId);
    
    // Update reserva
    reserva.setEstado(EstadoReserva.CONFIRMADA);
    reserva.setQrToken(qrGenerado.getCodigoQR()); // ← Usar el código QR generado
    reservaRepository.save(reserva);

    return pago;
}

    public Optional<Pago> findById(String id) {
        return pagoRepository.findById(id);
    }

    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public List<Pago> findByReservaId(String reservaId) {
        return pagoRepository.findByReservaId(reservaId);
    }

    public Pago reintentarPago(String id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (pago.getEstado() != EstadoPago.FALLIDO) {
            throw new RuntimeException("Solo se pueden reintentar pagos fallidos");
        }

        // Simular reintento exitoso
        pago.setEstado(EstadoPago.COMPLETADO);
        pago.setFechaPago(LocalDateTime.now());
        pago.setTokenPago("retry_" + System.currentTimeMillis());

        return pagoRepository.save(pago);
    }
}