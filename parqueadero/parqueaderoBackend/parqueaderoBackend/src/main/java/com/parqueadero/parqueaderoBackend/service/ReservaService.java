package com.parqueadero.parqueaderoBackend.service;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.dto.ReservaRequestDTO;
import com.parqueadero.parqueaderoBackend.dto.ReservaResponseDTO;
import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.repository.CupoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;
import com.parqueadero.parqueaderoBackend.repository.UsuarioRepository;

@Service
public class ReservaService {

    private ReservaRepository reservaRepository;
    private UsuarioRepository usuarioRepository;
    private CupoRepository cupoRepository;
    private QrService qrService;

    public ReservaService() {}

    public ReservaService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository, CupoRepository cupoRepository, QrService qrService) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.cupoRepository = cupoRepository;
        this.qrService = qrService;
    }

    public ReservaRepository getReservaRepository() {
        return reservaRepository;
    }

    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public CupoRepository getCupoRepository() {
        return cupoRepository;
    }

    public void setCupoRepository(CupoRepository cupoRepository) {
        this.cupoRepository = cupoRepository;
    }

    public QrService getQrService() {
        return qrService;
    }

    public void setQrService(QrService qrService) {
        this.qrService = qrService;
    }

    @Override
    public String toString() {
        return "ReservaService{" +
                "reservaRepository=" + reservaRepository +
                ", usuarioRepository=" + usuarioRepository +
                ", cupoRepository=" + cupoRepository +
                ", qrService=" + qrService +
                '}';
    }

    public com.parqueadero.parqueaderoBackend.model.Reserva createReserva(ReservaRequestDTO request) {
        // Validate usuario
        com.parqueadero.parqueaderoBackend.model.Usuario usuarioModel = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validate cupo
        com.parqueadero.parqueaderoBackend.model.Cupo cupoModel = cupoRepository.findById(request.getCupoId())
                .orElseThrow(() -> new RuntimeException("Cupo no encontrado"));

        // Check overlap
        List<com.parqueadero.parqueaderoBackend.model.Reserva> overlapping = reservaRepository.findByCupoIdAndEstadoInAndFechaInicioLessThanAndFechaFinGreaterThan(
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
        double total = hours * cupoModel.getPrecio();

        // Create reserva
        com.parqueadero.parqueaderoBackend.model.Reserva reservaModel = com.parqueadero.parqueaderoBackend.model.Reserva.builder()
                .usuarioId(request.getUsuarioId())
                .cupoId(request.getCupoId())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .total(total)
                .estado(EstadoReserva.PENDIENTE)
                .build();

        return reservaRepository.save(reservaModel);
    }

    public ReservaResponseDTO getReservaResponse(String id) {
        com.parqueadero.parqueaderoBackend.model.Reserva reservaModel = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        com.parqueadero.parqueaderoBackend.model.Usuario usuarioModel = usuarioRepository.findById(reservaModel.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        com.parqueadero.parqueaderoBackend.model.Cupo cupoModel = cupoRepository.findById(reservaModel.getCupoId())
                .orElseThrow(() -> new RuntimeException("Cupo no encontrado"));

        return ReservaResponseDTO.builder()
                .id(reservaModel.getId())
                .usuarioNombre(usuarioModel.getNombre())
                .cupoNumero(cupoModel.getNumero())
                .fechaInicio(reservaModel.getFechaInicio())
                .fechaFin(reservaModel.getFechaFin())
                .total(reservaModel.getTotal())
                .estado(reservaModel.getEstado())
                .qrToken(reservaModel.getQrToken())
                .build();
    }

    public List<com.parqueadero.parqueaderoBackend.model.Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<com.parqueadero.parqueaderoBackend.model.Reserva> findById(String id) {
        return reservaRepository.findById(id);
    }

    public Optional<com.parqueadero.parqueaderoBackend.model.Reserva> findByQrToken(String qrToken) {
        return reservaRepository.findByQrToken(qrToken);
    }

    public com.parqueadero.parqueaderoBackend.model.Reserva save(com.parqueadero.parqueaderoBackend.model.Reserva reservaModel) {
        return reservaRepository.save(reservaModel);
    }
}