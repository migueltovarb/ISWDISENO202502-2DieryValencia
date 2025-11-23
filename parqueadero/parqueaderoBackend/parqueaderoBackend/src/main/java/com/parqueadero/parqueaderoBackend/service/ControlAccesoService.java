package com.parqueadero.parqueaderoBackend.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.model.EstadoRegistroAcceso;
import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.RegistroAcceso;
import com.parqueadero.parqueaderoBackend.repository.RegistroAccesoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;

@Service
public class ControlAccesoService {

    private ReservaRepository reservaRepository;
    private RegistroAccesoRepository registroAccesoRepository;

    public ControlAccesoService() {}

    public ControlAccesoService(ReservaRepository reservaRepository, RegistroAccesoRepository registroAccesoRepository) {
        this.reservaRepository = reservaRepository;
        this.registroAccesoRepository = registroAccesoRepository;
    }

    public ReservaRepository getReservaRepository() {
        return reservaRepository;
    }

    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public RegistroAccesoRepository getRegistroAccesoRepository() {
        return registroAccesoRepository;
    }

    public void setRegistroAccesoRepository(RegistroAccesoRepository registroAccesoRepository) {
        this.registroAccesoRepository = registroAccesoRepository;
    }

    @Override
    public String toString() {
        return "ControlAccesoService{" +
                "reservaRepository=" + reservaRepository +
                ", registroAccesoRepository=" + registroAccesoRepository +
                '}';
    }

    public String validarEntrada(String qrToken) {
        com.parqueadero.parqueaderoBackend.model.Reserva reservaModel = reservaRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new RuntimeException("QR inválido"));

        if (reservaModel.getEstado() != EstadoReserva.PAGADA) {
            throw new RuntimeException("Reserva no pagada");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(reservaModel.getFechaInicio().minusMinutes(15))) {
            throw new RuntimeException("Demasiado temprano para ingresar");
        }

        // Check if already entered
        Optional<RegistroAcceso> existing = registroAccesoRepository.findByReservaIdAndEstado(reservaModel.getId(), EstadoRegistroAcceso.EN_PARQUEADERO);
        if (existing.isPresent()) {
            throw new RuntimeException("Ya se ha ingresado con esta reserva");
        }

        // Create registro
        RegistroAcceso registro = new RegistroAcceso(
                null,
                reservaModel.getId(),
                now,
                null,
                EstadoRegistroAcceso.EN_PARQUEADERO
        );

        registroAccesoRepository.save(registro);

        reservaModel.setEstado(EstadoReserva.ACTIVA);
        reservaRepository.save(reservaModel);

        return "Acceso Concedido";
    }

    public String registrarSalida(String qrToken) {
        com.parqueadero.parqueaderoBackend.model.Reserva reservaModel = reservaRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new RuntimeException("QR inválido"));

        RegistroAcceso registro = registroAccesoRepository.findByReservaIdAndEstado(reservaModel.getId(), EstadoRegistroAcceso.EN_PARQUEADERO)
                .orElseThrow(() -> new RuntimeException("No hay registro de entrada activo"));

        LocalDateTime now = LocalDateTime.now();
        registro.setFechaSalida(now);
        registro.setEstado(EstadoRegistroAcceso.FINALIZADO);
        registroAccesoRepository.save(registro);

        // Calculate extra if any
        if (now.isAfter(reservaModel.getFechaFin())) {
            long extraHours = ChronoUnit.HOURS.between(reservaModel.getFechaFin(), now);
            // Logic for extra charge, e.g. add to total or notify
            System.out.println("Horas extra: " + extraHours);
        }

        reservaModel.setEstado(EstadoReserva.FINALIZADA);
        reservaRepository.save(reservaModel);

        return "Salida Registrada";
    }
}