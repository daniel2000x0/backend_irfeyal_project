package com.irfeyal.rolseguridad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.irfeyal.rolseguridad.modelo.RolUsuario;

public interface RolUsuarioDAO extends CrudRepository<RolUsuario, Long> {

  @Query(value = "SELECT r.descripcion FROM rol_usuario ru "
      + "JOIN rol r ON r.id_rol = ru.id_rol "
      + "WHERE ru.id_usuario = ?1 AND ru.estado = true",
      nativeQuery = true)
  List<String> validacionadmin(Long usuario);

}
