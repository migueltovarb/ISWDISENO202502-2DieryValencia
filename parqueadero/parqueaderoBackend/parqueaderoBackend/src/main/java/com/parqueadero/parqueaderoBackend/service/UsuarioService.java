package com.parqueadero.parqueaderoBackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService() {}

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public String toString() {
        return "UsuarioService{" +
                "usuarioRepository=" + usuarioRepository +
                '}';
    }

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