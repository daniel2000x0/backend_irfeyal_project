package com.irfeyal.rolseguridad.dao;

import org.springframework.data.repository.CrudRepository;

import com.irfeyal.rolseguridad.modelo.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

}
