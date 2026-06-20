package com.irfeyal.parametrizacionacademica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.irfeyal.parametrizacionacademica.modelo.Periodo;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

  @Query(value = "SELECT DISTINCT p.* FROM periodo p "
      + "JOIN matricula m ON m.id_periodo = p.id_periodo "
      + "WHERE m.id_empleado = ?1",
      nativeQuery = true)
  List<Periodo> listarperiodoasistencia(Long empleado);

}
