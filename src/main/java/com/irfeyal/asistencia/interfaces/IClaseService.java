package com.irfeyal.asistencia.interfaces;

import java.util.Date;
import java.util.List;

import com.irfeyal.asistencia.dto.ClaseDTO;

/**
 * Servicio de Clase.
 * CRUD + validación de clase duplicada + consulta de fechas
 * de faltas para reportes.
 */
public interface IClaseService {

  ClaseDTO save(ClaseDTO claseDTO);
  List<ClaseDTO> findAll();
  ClaseDTO findById(Long id);
  ClaseDTO ultimoingreso();

  List<ClaseDTO> validarclase(Integer idDocente, Integer idPeriodo, Integer idModalidad,
      Integer idCurso, Integer idParalelo, Integer idAsignatura, Date fecha);

  Long validarclass(Integer idDocente, Integer idPeriodo, Integer idModalidad,
      Integer idCurso, Integer idParalelo, Integer idAsignatura, Date fecha);

  List<ClaseDTO> mostrarfechas(Long idEstudiante, Integer idDocente, Integer idAsignatura,
      Integer idCurso, Integer idParalelo, Integer idModalidad, Integer idPeriodo);

}
