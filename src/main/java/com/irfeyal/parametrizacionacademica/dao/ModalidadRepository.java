package com.irfeyal.parametrizacionacademica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.irfeyal.parametrizacionacademica.modelo.Modalidad;

@Repository
public interface ModalidadRepository extends JpaRepository<Modalidad, Long> {

  @Query(value = "SELECT DISTINCT m.* FROM modalidad m "
      + "JOIN matricula mat ON mat.id_modalidad = m.id_modalidad "
      + "JOIN empleado e ON e.id_empleado = ?1 "
      + "WHERE mat.id_periodo = ?2",
      nativeQuery = true)
  List<Modalidad> listarmodalidadasistencia(Long empleado, Long idPeriodo);

}
