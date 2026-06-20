package com.irfeyal.parametrizacionacademica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.irfeyal.parametrizacionacademica.modelo.Paralelo;

@Repository
public interface ParaleloRespository extends JpaRepository<Paralelo, Long> {

  @Query(value = "SELECT DISTINCT p.* FROM paralelo p "
      + "JOIN matricula m ON m.id_paralelo = p.id_paralelo "
      + "WHERE m.id_empleado = ?1 AND m.id_periodo = ?2 "
      + "AND m.id_modalidad = ?3 AND m.id_curso = ?4",
      nativeQuery = true)
  List<Paralelo> listarparaleloasistencia(Long empleado, Long periodo,
      Long modalidad, Long idCurso);

}
