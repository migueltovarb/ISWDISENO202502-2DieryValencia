package com.parqueadero.parqueaderoBackend.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.model.Cupo;
import com.parqueadero.parqueaderoBackend.model.EstadoCupo;
import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.Reserva;
import com.parqueadero.parqueaderoBackend.model.Usuario;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CupoService cupoService;
    private final UsuarioService usuarioService;

    public ReservaService(ReservaRepository reservaRepository, CupoService cupoService, UsuarioService usuarioService) {
        this.reservaRepository = reservaRepository;
        this.cupoService = cupoService;
        this.usuarioService = usuarioService;
    }

    public Reserva crearReserva(Reserva reserva) {
        // Validar usuario existe
        Optional<Usuario> usuario = usuarioService.findById(reserva.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Validar cupo existe
        Optional<Cupo> cupo = cupoService.findById(reserva.getCupoId());
        if (cupo.isEmpty()) {
            throw new RuntimeException("Cupo no encontrado");
        }

        // Validar cupo disponible
        if (cupo.get().getEstado() != EstadoCupo.DISPONIBLE) {
            throw new RuntimeException("Cupo no disponible");
        }

        // Validar fechas
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio()) || reserva.getFechaFin().equals(reserva.getFechaInicio())) {
            throw new RuntimeException("Fecha fin debe ser posterior a fecha inicio");
        }

        if (reserva.getFechaInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Fecha inicio no puede estar en el pasado");
        }

        // Verificar no hay reservas activas en esas fechas
        List<Reserva> overlapping = reservaRepository.findByCupoIdAndEstadoIn(
                reserva.getCupoId(),
                Arrays.asList(EstadoReserva.CONFIRMADA, EstadoReserva.EN_USO)
        );
        for (Reserva r : overlapping) {
            if (!(reserva.getFechaFin().isBefore(r.getFechaInicio()) || reserva.getFechaInicio().isAfter(r.getFechaFin()))) {
                throw new RuntimeException("Cupo ocupado en esas fechas");
            }
        }

        // Calcular costo estimado
        double costoEstimado = calcularCosto(reserva.getFechaInicio(), reserva.getFechaFin(), cupo.get().getPrecio());
        reserva.setCostoEstimado(costoEstimado);
        reserva.setEstado(EstadoReserva.PENDIENTE_PAGO);
        reserva.setFechaCreacion(LocalDateTime.now());

        return reservaRepository.save(reserva);
    }

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(String id) {
        return reservaRepository.findById(id);
    }

    public List<Reserva> findByUsuarioId(String usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public List<Reserva> findByCupoId(String cupoId) {
        return reservaRepository.findByCupoId(cupoId);
    }

    public Reserva cambiarEstado(String id, EstadoReserva nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }

    public Reserva cancelarReserva(String id) {
        return cambiarEstado(id, EstadoReserva.CANCELADA);
    }

    public Reserva confirmarReserva(String id) {
        Reserva reserva = cambiarEstado(id, EstadoReserva.CONFIRMADA);
        // Cambiar cupo a OCUPADO
        cupoService.findById(reserva.getCupoId()).ifPresent(cupo -> {
            cupo.setEstado(EstadoCupo.OCUPADO);
            cupoService.save(cupo);
        });
        return reserva;
    }

    public Reserva completarReserva(String id, Double costoFinal) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado(EstadoReserva.COMPLETADA);
        reserva.setCostoFinal(costoFinal);
        Reserva saved = reservaRepository.save(reserva);
        // Liberar cupo
        cupoService.findById(reserva.getCupoId()).ifPresent(cupo -> {
            cupo.setEstado(EstadoCupo.DISPONIBLE);
            cupoService.save(cupo);
        });
        return saved;
    }

    public double calcularCosto(LocalDateTime inicio, LocalDateTime fin, Double tarifaPorHora) {
        long horas = ChronoUnit.HOURS.between(inicio, fin);
        return horas * tarifaPorHora;
    }

    public Optional<Reserva> findByQrToken(String qrToken) {
        return reservaRepository.findByQrToken(qrToken);
    }

    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
}