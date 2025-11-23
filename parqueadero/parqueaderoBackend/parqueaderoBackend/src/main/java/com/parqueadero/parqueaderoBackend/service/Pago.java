package com.parqueadero.parqueaderoBackend.service;

import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.Pago;
import com.parqueadero.parqueaderoBackend.model.Reserva;
import com.parqueadero.parqueaderoBackend.repository.PagoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Pago {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private Qr qrService;

    public Pago processPago(String reservaId, double monto, String medioPago) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw new RuntimeException("Reserva no está pendiente de pago");
        }

        // Assume payment is successful
        Pago pago = Pago.builder()
                .reservaId(reservaId)
                .monto(monto)
                .fecha(LocalDateTime.now())
                .medioPago(medioPago)
                .build();

        pagoRepository.save(pago);

        // Update reserva
        reserva.setEstado(EstadoReserva.PAGADA);
        reserva.setQrToken(qrService.generateQrToken());
        reservaRepository.save(reserva);

        return pago;
    }
}