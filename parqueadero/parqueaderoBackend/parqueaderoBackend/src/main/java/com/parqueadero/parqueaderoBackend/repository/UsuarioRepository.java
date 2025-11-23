package com.parqueadero.parqueaderoBackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.parqueadero.parqueaderoBackend.model.TipoUsuario;
import com.parqueadero.parqueaderoBackend.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByPlaca(String placa);
    List<Usuario> findByTipo(TipoUsuario tipo);
}