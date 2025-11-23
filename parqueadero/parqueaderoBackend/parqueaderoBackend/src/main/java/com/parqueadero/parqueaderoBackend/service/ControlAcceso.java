package com.parqueadero.parqueaderoBackend.service;

import com.parqueadero.parqueaderoBackend.model.*;
import com.parqueadero.parqueaderoBackend.repository.RegistroAccesoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class ControlAcceso {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RegistroAccesoRepository registroAccesoRepository;

    public String validarEntrada(String qrToken) {
        Reserva reserva = reservaRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new RuntimeException("QR inválido"));

        if (reserva.getEstado() != EstadoReserva.PAGADA) {
            throw new RuntimeException("Reserva no pagada");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(reserva.getFechaInicio().minusMinutes(15))) {
            throw new RuntimeException("Demasiado temprano para ingresar");
        }

        // Check if already entered
        Optional<RegistroAcceso> existing = registroAccesoRepository.findByReservaIdAndEstado(reserva.getId(), EstadoRegistroAcceso.EN_PARQUEADERO);
        if (existing.isPresent()) {
            throw new RuntimeException("Ya se ha ingresado con esta reserva");
        }

        // Create registro
        RegistroAcceso registro = RegistroAcceso.builder()
                .reservaId(reserva.getId())
                .fechaEntrada(now)
                .estado(EstadoRegistroAcceso.EN_PARQUEADERO)
                .build();

        registroAccesoRepository.save(registro);

        reserva.setEstado(EstadoReserva.ACTIVA);
        reservaRepository.save(reserva);

        return "Acceso Concedido";
    }

    public String registrarSalida(String qrToken) {
        Reserva reserva = reservaRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new RuntimeException("QR inválido"));

        RegistroAcceso registro = registroAccesoRepository.findByReservaIdAndEstado(reserva.getId(), EstadoRegistroAcceso.EN_PARQUEADERO)
                .orElseThrow(() -> new RuntimeException("No hay registro de entrada activo"));

        LocalDateTime now = LocalDateTime.now();
        registro.setFechaSalida(now);
        registro.setEstado(EstadoRegistroAcceso.FINALIZADO);
        registroAccesoRepository.save(registro);

        // Calculate extra if any
        if (now.isAfter(reserva.getFechaFin())) {
            long extraHours = ChronoUnit.HOURS.between(reserva.getFechaFin(), now);
            // Logic for extra charge, e.g. add to total or notify
            System.out.println("Horas extra: " + extraHours);
        }

        reserva.setEstado(EstadoReserva.FINALIZADA);
        reservaRepository.save(reserva);

        return "Salida Registrada";
    }
}