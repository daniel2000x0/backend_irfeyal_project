package com.irfeyal.parametrizacionacademica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.irfeyal.parametrizacionacademica.modelo.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

  @Query(value = "SELECT DISTINCT c.* FROM curso c "
      + "JOIN matricula m ON m.id_curso = c.id_curso "
      + "JOIN empleado e ON e.id_empleado = ?1 "
      + "WHERE m.id_modalidad = ?3 AND m.id_periodo = ?2",
      nativeQuery = true)
  List<Curso> listarcursoasistencia(Long empleado, Long periodo, Long idModalidad);

}
