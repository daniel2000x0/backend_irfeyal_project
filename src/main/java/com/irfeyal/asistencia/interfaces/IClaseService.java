package com.irfeyal.asistencia.interfaces;

import java.util.Date;
import java.util.List;

import com.irfeyal.asistencia.modelo.Clase;

public interface IClaseService {

  Clase save(Clase clase);
  List<Clase> findAll();
  Clase findById(Long id);
  Clase ultimoingreso();

  List<Clase> validarclase(Integer idDocente, Integer idPeriodo, Integer idModalidad,
      Integer idCurso, Integer idParalelo, Integer idAsignatura, Date fecha);

  Long validarclass(Integer idDocente, Integer idPeriodo, Integer idModalidad,
      Integer idCurso, Integer idParalelo, Integer idAsignatura, Date fecha);

  List<Clase> mostrarfechas(Long idEstudiante, Integer idDocente, Integer idAsignatura,
      Integer idCurso, Integer idParalelo, Integer idModalidad, Integer idPeriodo);

}
