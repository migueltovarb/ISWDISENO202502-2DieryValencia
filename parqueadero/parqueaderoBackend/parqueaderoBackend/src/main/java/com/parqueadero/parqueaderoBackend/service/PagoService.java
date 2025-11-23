package com.parqueadero.parqueaderoBackend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.Pago;
import com.parqueadero.parqueaderoBackend.model.Reserva;
import com.parqueadero.parqueaderoBackend.repository.PagoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;

@Service
public class PagoService {

    private PagoRepository pagoRepository;
    private ReservaRepository reservaRepository;
    private QrService qrService;

    public PagoService() {}

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

    @Override
    public String toString() {
        return "PagoService{" +
                "pagoRepository=" + pagoRepository +
                ", reservaRepository=" + reservaRepository +
                ", qrService=" + qrService +
                '}';
    }

    public Pago processPago(String reservaId, Double monto, String medioPago) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw new RuntimeException("Reserva no está pendiente de pago");
        }

        // Assume payment is successful
        Pago pago = new Pago(
                null,
                reservaId,
                monto,
                LocalDateTime.now(),
                medioPago
        );

        pagoRepository.save(pago);

        // Update reserva
        reserva.setEstado(EstadoReserva.PAGADA);
        reserva.setQrToken(qrService.generateQrToken());
        reservaRepository.save(reserva);

        return pago;
    }

    public Optional<Pago> findById(String id) {
        return pagoRepository.findById(id);
    }
}