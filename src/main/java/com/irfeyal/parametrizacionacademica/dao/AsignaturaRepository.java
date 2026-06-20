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
      + "JOIN empleado e ON e.id_empleado = ae.id_empleado "
      + "JOIN periodo p ON p.id_periodo = ?2 "
      + "WHERE ae.id_empleado = ?1 AND p.id_periodo = ?2",
      nativeQuery = true)
  List<Asignatura> listarasignaturaasistencia(Long empleado, Long idPeriodo,
      Long idMod, Long idCurso, Long idParalelo);

}
