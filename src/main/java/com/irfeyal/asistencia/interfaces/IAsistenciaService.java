package com.irfeyal.asistencia.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.irfeyal.asistencia.dto.AsistenciaDTO;
import com.irfeyal.matricula.modelo.Estudiante;
import com.irfeyal.parametrizacionacademica.modelo.Asignatura;
import com.irfeyal.parametrizacionacademica.modelo.Curso;
import com.irfeyal.parametrizacionacademica.modelo.Modalidad;
import com.irfeyal.parametrizacionacademica.modelo.Paralelo;
import com.irfeyal.parametrizacionacademica.modelo.Periodo;
import com.irfeyal.rolseguridad.modelo.Persona;

public interface IAsistenciaService {

  List<AsistenciaDTO> findAll();
  Page<AsistenciaDTO> findAll(Pageable pageable);
  AsistenciaDTO findById(Long id);
  AsistenciaDTO save(AsistenciaDTO asistenciaDTO);
  void delete(Long id);
  List<Persona> buscarCurso(Long id);
  List<Estudiante> buscarCursoModalidad(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso);
  Estudiante buscarCedulaEstudiante(String cedula);

  List<Asignatura> findAllAsignaturas();
  List<Paralelo> findAllParalelo();
  List<Modalidad> findAllModalidad();
  List<Periodo> findAllPeriodo();
  List<Curso> findAllCurso();

  List<Estudiante> mostrarInformacion(long id);
  List<AsistenciaDTO> buscarAsistencia(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha, Long docente);

  List<Periodo> listarPeriodo(Long empleado);
  List<Modalidad> listarModalidad(Long empleado, Long idPeriodo);
  List<Curso> listarCurso(Long empleado, Long periodo, Long idModalidad);
  List<Paralelo> listarParalelo(Long empleado, Long periodo, Long modalidad, Long idCurso);
  List<Asignatura> listarAsignatura(Long empleado, Long idPeriodo, Long modalidad,
      Long idCurso, Long idParalelo);

  ResponseEntity<ByteArrayResource> exportInvoice(Long idEstudiante, Long idDocente,
      Long idAsignatura, Long usuario, Date fechaInicio, Date fechaFin);

  ResponseEntity<ByteArrayResource> exportInvoiceCurso(Long idMod, Long idPeriodo,
      Long idParalelo, Long idAsignatura, Long idCurso, Long docente, Long usuario,
      Date fechaInicio, Date fechaFin);

}
