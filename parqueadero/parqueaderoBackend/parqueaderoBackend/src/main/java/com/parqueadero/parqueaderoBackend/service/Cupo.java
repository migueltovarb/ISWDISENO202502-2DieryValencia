package com.parqueadero.parqueaderoBackend.service;

import com.parqueadero.parqueaderoBackend.repository.CupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Cupo {

    @Autowired
    private CupoRepository cupoRepository;

    public com.parqueadero.parqueaderoBackend.model.Cupo save(com.parqueadero.parqueaderoBackend.model.Cupo cupo) {
        return cupoRepository.save(cupo);
    }

    public List<com.parqueadero.parqueaderoBackend.model.Cupo> findAll() {
        return cupoRepository.findAll();
    }

    public Optional<com.parqueadero.parqueaderoBackend.model.Cupo> findById(String id) {
        return cupoRepository.findById(id);
    }

    public void deleteById(String id) {
        cupoRepository.deleteById(id);
    }
}