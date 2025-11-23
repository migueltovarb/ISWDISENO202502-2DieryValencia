package com.parqueadero.parqueaderoBackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.repository.CupoRepository;

@Service
public class CupoService {

    private CupoRepository cupoRepository;

    public CupoService() {}

    public CupoService(CupoRepository cupoRepository) {
        this.cupoRepository = cupoRepository;
    }

    public CupoRepository getCupoRepository() {
        return cupoRepository;
    }

    public void setCupoRepository(CupoRepository cupoRepository) {
        this.cupoRepository = cupoRepository;
    }

    @Override
    public String toString() {
        return "CupoService{" +
                "cupoRepository=" + cupoRepository +
                '}';
    }

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