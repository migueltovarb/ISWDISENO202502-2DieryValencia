package com.parqueadero.parqueaderoBackend.service;

import com.parqueadero.parqueaderoBackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Usuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public com.parqueadero.parqueaderoBackend.model.Usuario save(com.parqueadero.parqueaderoBackend.model.Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<com.parqueadero.parqueaderoBackend.model.Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<com.parqueadero.parqueaderoBackend.model.Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<com.parqueadero.parqueaderoBackend.model.Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }
}