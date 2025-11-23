package com.parqueadero.parqueaderoBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.parqueaderoBackend.model.Usuario;
import com.parqueadero.parqueaderoBackend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(201).body(savedUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String id) {
        Usuario usuario = usuarioService.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario updatedUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}