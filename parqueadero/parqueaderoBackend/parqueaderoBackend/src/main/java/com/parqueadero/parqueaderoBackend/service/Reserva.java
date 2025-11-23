package com.parqueadero.parqueaderoBackend.service;

import com.parqueadero.parqueaderoBackend.dto.ReservaRequestDTO;
import com.parqueadero.parqueaderoBackend.dto.ReservaResponseDTO;
import com.parqueadero.parqueaderoBackend.model.*;
import com.parqueadero.parqueaderoBackend.repository.CupoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;
import com.parqueadero.parqueaderoBackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class Reserva {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CupoRepository cupoRepository;

    @Autowired
    private Qr qrService;

    public com.parqueadero.parqueaderoBackend.model.Reserva createReserva(ReservaRequestDTO request) {
        // Validate usuario
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validate cupo
        Cupo cupo = cupoRepository.findById(request.getCupoId())
                .orElseThrow(() -> new RuntimeException("Cupo no encontrado"));

        // Check overlap
        List<Reserva> overlapping = reservaRepository.findByCupoIdAndEstadoInAndFechaInicioLessThanAndFechaFinGreaterThan(
                request.getCupoId(),
                Arrays.asList(EstadoReserva.PAGADA, EstadoReserva.ACTIVA),
                request.getFechaFin(),
                request.getFechaInicio()
        );
        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Cupo ocupado en ese horario");
        }

        // Calculate total
        long hours = ChronoUnit.HOURS.between(request.getFechaInicio(), request.getFechaFin());
        double total = hours * cupo.getPrecio();

        // Create reserva
        Reserva reserva = Reserva.builder()
                .usuarioId(request.getUsuarioId())
                .cupoId(request.getCupoId())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .total(total)
                .estado(EstadoReserva.PENDIENTE)
                .build();

        return reservaRepository.save(reserva);
    }

    public ReservaResponseDTO getReservaResponse(String id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Usuario usuario = usuarioRepository.findById(reserva.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cupo cupo = cupoRepository.findById(reserva.getCupoId())
                .orElseThrow(() -> new RuntimeException("Cupo no encontrado"));

        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .usuarioNombre(usuario.getNombre())
                .cupoNumero(cupo.getNumero())
                .fechaInicio(reserva.getFechaInicio())
                .fechaFin(reserva.getFechaFin())
                .total(reserva.getTotal())
                .estado(reserva.getEstado())
                .qrToken(reserva.getQrToken())
                .build();
    }

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(String id) {
        return reservaRepository.findById(id);
    }

    public Optional<Reserva> findByQrToken(String qrToken) {
        return reservaRepository.findByQrToken(qrToken);
    }

    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
}