package com.parqueadero.parqueaderoBackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parqueadero.parqueaderoBackend.model.Usuario;
import com.parqueadero.parqueaderoBackend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario save(Usuario usuario) {
        // Validar que la placa no esté duplicada
        if (usuario.getPlaca() != null && !usuario.getPlaca().isEmpty()) {
            Optional<Usuario> existing = usuarioRepository.findByPlaca(usuario.getPlaca());
            if (existing.isPresent() && !existing.get().getId().equals(usuario.getId())) {
                throw new RuntimeException("La placa ya está registrada");
            }
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByPlaca(String placa) {
        return usuarioRepository.findByPlaca(placa);
    }

    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }
}