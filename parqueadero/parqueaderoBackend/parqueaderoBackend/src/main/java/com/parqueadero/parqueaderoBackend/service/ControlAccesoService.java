package com.parqueadero.parqueaderoBackend.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.model.EstadoReserva;
import com.parqueadero.parqueaderoBackend.model.RegistroAcceso;
import com.parqueadero.parqueaderoBackend.model.TipoAcceso;
import com.parqueadero.parqueaderoBackend.repository.RegistroAccesoRepository;
import com.parqueadero.parqueaderoBackend.repository.ReservaRepository;

@Service
public class ControlAccesoService {

    private ReservaRepository reservaRepository;
    private RegistroAccesoRepository registroAccesoRepository;
    private com.parqueadero.parqueaderoBackend.service.QrService qrService;

    
    @Autowired
    public ControlAccesoService(ReservaRepository reservaRepository, RegistroAccesoRepository registroAccesoRepository, com.parqueadero.parqueaderoBackend.service.QrService qrService) {
        this.reservaRepository = reservaRepository;
        this.registroAccesoRepository = registroAccesoRepository;
        this.qrService = qrService;
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

    public com.parqueadero.parqueaderoBackend.service.QrService getQrService() {
        return qrService;
    }

    public void setQrService(com.parqueadero.parqueaderoBackend.service.QrService qrService) {
        this.qrService = qrService;
    }

    @Override
    public String toString() {
        return "ControlAccesoService{" +
                "reservaRepository=" + reservaRepository +
                ", registroAccesoRepository=" + registroAccesoRepository +
                ", qrService=" + qrService +
                '}';
    }

    public String validarEntrada(String qrToken) {
        com.parqueadero.parqueaderoBackend.model.Reserva reservaModel = reservaRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new RuntimeException("QR inválido"));

        if (reservaModel.getEstado() != EstadoReserva.CONFIRMADA) {
            throw new RuntimeException("Reserva no confirmada");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(reservaModel.getFechaInicio().minusMinutes(15))) {
            throw new RuntimeException("Demasiado temprano para ingresar");
        }

        // Check if already entered
        boolean alreadyEntered = registroAccesoRepository.findAll().stream()
                .anyMatch(r -> r.getReservaId().equals(reservaModel.getId()) &&
                               r.getTipoAcceso() == TipoAcceso.ENTRADA &&
                               r.isExitoso());
        if (alreadyEntered) {
            throw new RuntimeException("Ya se ha ingresado con esta reserva");
        }

        // Create registro
        RegistroAcceso registro = new RegistroAcceso(
                null,
                reservaModel.getId(),
                null, // qrId
                TipoAcceso.ENTRADA,
                now,
                true,
                null,
                null
        );

        registroAccesoRepository.save(registro);

        reservaModel.setEstado(EstadoReserva.EN_USO);
        reservaRepository.save(reservaModel);

        return "Acceso Concedido";
    }

    public String registrarSalida(String qrToken) {
        com.parqueadero.parqueaderoBackend.model.Reserva reservaModel = reservaRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new RuntimeException("QR inválido"));

        // Check if entered
        boolean hasEntered = registroAccesoRepository.findAll().stream()
                .anyMatch(r -> r.getReservaId().equals(reservaModel.getId()) &&
                               r.getTipoAcceso() == TipoAcceso.ENTRADA &&
                               r.isExitoso());
        if (!hasEntered) {
            throw new RuntimeException("No hay registro de entrada activo");
        }

        LocalDateTime now = LocalDateTime.now();

        // Create salida registro
        RegistroAcceso registroSalida = new RegistroAcceso(
                null,
                reservaModel.getId(),
                null, // qrId
                TipoAcceso.SALIDA,
                now,
                true,
                null,
                null
        );
        registroAccesoRepository.save(registroSalida);

        // Calculate extra if any
        if (now.isAfter(reservaModel.getFechaFin())) {
            long extraHours = ChronoUnit.HOURS.between(reservaModel.getFechaFin(), now);
            // Logic for extra charge, e.g. add to total or notify
            System.out.println("Horas extra: " + extraHours);
        }

        reservaModel.setEstado(EstadoReserva.COMPLETADA);
        reservaRepository.save(reservaModel);

        return "Salida Registrada";
    }

    public String registrarEntrada(String qrId, String reservaId) {
        // Validar QR
        boolean qrValido = qrService.validarQR(qrId);
        if (!qrValido) {
            // Registrar intento fallido
            RegistroAcceso registroFallido = new RegistroAcceso(
                    null, reservaId, qrId, TipoAcceso.ENTRADA, LocalDateTime.now(), false, "QR inválido", null
            );
            registroAccesoRepository.save(registroFallido);
            throw new RuntimeException("QR inválido");
        }

        // Obtener reserva
        com.parqueadero.parqueaderoBackend.model.Reserva reservaModel = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reservaModel.getEstado() != EstadoReserva.CONFIRMADA) {
            throw new RuntimeException("Reserva no está confirmada");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(reservaModel.getFechaInicio().minusMinutes(15))) {
            throw new RuntimeException("Demasiado temprano para ingresar");
        }

        // Check if already entered
        boolean alreadyEntered = registroAccesoRepository.findAll().stream()
                .anyMatch(r -> r.getReservaId().equals(reservaId) &&
                               r.getTipoAcceso() == TipoAcceso.ENTRADA &&
                               r.isExitoso());
        if (alreadyEntered) {
            throw new RuntimeException("Ya se ha ingresado con esta reserva");
        }

        // Create registro
        RegistroAcceso registro = new RegistroAcceso(
                null,
                reservaId,
                qrId,
                TipoAcceso.ENTRADA,
                now,
                true,
                null,
                null
        );

        registroAccesoRepository.save(registro);

        reservaModel.setEstado(EstadoReserva.EN_USO);
        reservaRepository.save(reservaModel);

        return "Acceso Concedido";
    }

    public String registrarSalida(String qrId, String reservaId) {
        // Validar QR
        boolean qrValido = qrService.validarQR(qrId);
        if (!qrValido) {
            RegistroAcceso registroFallido = new RegistroAcceso(
                    null, reservaId, qrId, TipoAcceso.SALIDA, LocalDateTime.now(), false, "QR inválido", null
            );
            registroAccesoRepository.save(registroFallido);
            throw new RuntimeException("QR inválido");
        }

        com.parqueadero.parqueaderoBackend.model.Reserva reservaModel = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Check if entered
        boolean hasEntered = registroAccesoRepository.findAll().stream()
                .anyMatch(r -> r.getReservaId().equals(reservaId) &&
                               r.getTipoAcceso() == TipoAcceso.ENTRADA &&
                               r.isExitoso());
        if (!hasEntered) {
            throw new RuntimeException("No hay registro de entrada activo");
        }

        LocalDateTime now = LocalDateTime.now();

        // Create salida registro
        RegistroAcceso registroSalida = new RegistroAcceso(
                null,
                reservaId,
                qrId,
                TipoAcceso.SALIDA,
                now,
                true,
                null,
                null
        );
        registroAccesoRepository.save(registroSalida);

        // Calculate extra if any
        if (now.isAfter(reservaModel.getFechaFin())) {
            long extraHours = ChronoUnit.HOURS.between(reservaModel.getFechaFin(), now);
            // Logic for extra charge
            System.out.println("Horas extra: " + extraHours);
        }

        reservaModel.setEstado(EstadoReserva.COMPLETADA);
        reservaRepository.save(reservaModel);

        return "Salida Registrada";
    }

    public List<RegistroAcceso> findByReservaId(String reservaId) {
        return registroAccesoRepository.findAll().stream()
                .filter(r -> r.getReservaId().equals(reservaId))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<RegistroAcceso> findByCupoId(String cupoId) {
        return registroAccesoRepository.findAll().stream()
                .filter(r -> {
                    // Obtener reserva y verificar cupo
                    return reservaRepository.findById(r.getReservaId())
                            .map(reserva -> reserva.getCupoId().equals(cupoId))
                            .orElse(false);
                })
                .collect(java.util.stream.Collectors.toList());
    }
}