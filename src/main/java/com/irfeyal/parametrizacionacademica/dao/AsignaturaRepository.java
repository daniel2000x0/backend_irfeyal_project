package com.irfeyal.parametrizacionacademica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.irfeyal.parametrizacionacademica.modelo.Asignatura;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

  @Query(value = "SELECT DISTINCT a.* FROM asignatura a "
      + "JOIN asignatura_empleado ae ON ae.id_asignatura = a.id_asignatura "
      + "JOIN matricula m ON m.id_empleado = ae.id_empleado "
      + "WHERE ae.id_empleado = ?1 AND m.id_periodo = ?2 "
      + "AND m.id_modalidad = ?3 AND m.id_curso = ?4 AND m.id_paralelo = ?5",
      nativeQuery = true)
  List<Asignatura> listarasignaturaasistencia(Long empleado, Long idPeriodo,
      Long idMod, Long idCurso, Long idParalelo);

}
