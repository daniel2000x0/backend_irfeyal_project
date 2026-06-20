package com.irfeyal.rolseguridad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.irfeyal.rolseguridad.modelo.Persona;

public interface PersonaDAO extends CrudRepository<Persona, Long> {

  @Query(value = "SELECT p.* FROM persona p "
      + "JOIN estudiantes e ON e.id_persona = p.id_persona "
      + "JOIN matricula m ON m.id_estudiante = e.id_estudiante "
      + "WHERE m.id_curso = ?1", nativeQuery = true)
  List<Persona> buscarcurso(Long id);

}
