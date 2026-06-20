package com.irfeyal.rolseguridad.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.irfeyal.rolseguridad.modelo.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.usuario = ?1")
    Optional<Usuario> findByUsuario(String usuario);

}
