package com.parqueadero.parqueaderoBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.model.Cupo;
import com.parqueadero.parqueaderoBackend.model.EstadoCupo;
import com.parqueadero.parqueaderoBackend.service.CupoService;

@RestController
@RequestMapping("/api/cupos")
public class CupoController {

    private final CupoService cupoService;

    public CupoController(CupoService cupoService) {
        this.cupoService = cupoService;
    }

    @PostMapping
    public ResponseEntity<Cupo> crearCupo(@RequestBody Cupo cupo) {
        Cupo savedCupo = cupoService.save(cupo);
        return ResponseEntity.status(201).body(savedCupo);
    }

    @GetMapping
    public ResponseEntity<List<Cupo>> obtenerTodos() {
        List<Cupo> cupos = cupoService.findAll();
        return ResponseEntity.ok(cupos);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Cupo>> obtenerCuposDisponibles() {
        List<Cupo> cupos = cupoService.findAll().stream()
                .filter(cupo -> cupo.getEstado() == EstadoCupo.DISPONIBLE)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cupos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cupo> actualizarCupo(@PathVariable String id, @RequestBody Cupo cupo) {
        cupo.setId(id);
        Cupo updatedCupo = cupoService.save(cupo);
        return ResponseEntity.ok(updatedCupo);
    }
}