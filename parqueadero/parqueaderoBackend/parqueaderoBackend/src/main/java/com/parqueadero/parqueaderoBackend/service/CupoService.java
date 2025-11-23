package com.parqueadero.parqueaderoBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parqueadero.parqueaderoBackend.repository.CupoRepository;
import com.parqueadero.parqueaderoBackend.model.Cupo;
import java.util.List;
import java.util.Optional;

@Service
public class CupoService {

    private final CupoRepository cupoRepository;

    // Inyección por constructor
    @Autowired
    public CupoService(CupoRepository cupoRepository) {
        this.cupoRepository = cupoRepository;
    }

    // Método para guardar un cupo
    public Cupo save(Cupo cupo) {
        return cupoRepository.save(cupo);
    }

    // Método para obtener todos los cupos
    public List<Cupo> findAll() {
        return cupoRepository.findAll();
    }

    // Método para buscar por ID
    public Optional<Cupo> findById(String id) {
        return cupoRepository.findById(id);
    }

    // Método para eliminar un cupo
    public void deleteById(String id) {
        cupoRepository.deleteById(id);
    }

    // Método para buscar cupos disponibles
    public List<Cupo> findByEstado(String estado) {
        return cupoRepository.findByEstado(estado);
    }

    // Método para buscar cupos por tipo
    public List<Cupo> findByTipo(String tipo) {
        return cupoRepository.findByTipo(tipo);
    }
}