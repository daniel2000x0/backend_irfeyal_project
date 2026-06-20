package com.irfeyal.asistencia.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import com.irfeyal.asistencia.modelo.Asistencia;
import com.irfeyal.matricula.modelo.Estudiante;
import com.irfeyal.parametrizacionacademica.modelo.Asignatura;
import com.irfeyal.parametrizacionacademica.modelo.Curso;
import com.irfeyal.parametrizacionacademica.modelo.Modalidad;
import com.irfeyal.parametrizacionacademica.modelo.Paralelo;
import com.irfeyal.parametrizacionacademica.modelo.Periodo;
import com.irfeyal.rolseguridad.modelo.Persona;

public interface IAsistenciaService {

  List<Asistencia> findAll();
  Asistencia findById(Long id);
  Asistencia save(Asistencia asistencia);
  void delete(Long id);
  List<Persona> buscarcurso(Long id);
  List<Estudiante> buscarcursomodalidad(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso);
  Estudiante buscarceduestudiante(String cedula);

  List<Asignatura> findAllAsignaturas();
  List<Paralelo> findAllParalelo();
  List<Modalidad> findAllModalidad();
  List<Periodo> findAllperio();
  List<Curso> findAllcurso();

  List<Estudiante> mostrarinformacion(long id);
  List<Asistencia> burcarasistencia(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha, Long docente);

  List<Periodo> listarpaeriodo(Long empleado);
  List<Modalidad> listarmodalidad(Long empleado, Long idPeriodo);
  List<Curso> listarcurso(Long empleado, Long periodo, Long idModalidad);
  List<Paralelo> listarparalelo(Long empleado, Long periodo, Long modalidad, Long idCurso);
  List<Asignatura> listarasignatura(Long empleado, Long idPeriodo, Long modalidad,
      Long idCurso, Long idParalelo);

  List<Modalidad> modalidaddocente(Integer idDocente);
  List<Asistencia> actualizarfiltros(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha, Long docente);

  ResponseEntity<ByteArrayResource> exportInvoice(Long idEstudiante, Long idDocente,
      Long idAsignatura, Long usuario, Date fechaInicio, Date fechaFin);

  ResponseEntity<ByteArrayResource> exportInvoicepdfcursos(Long idMod, Long idPeriodo,
      Long idParalelo, Long idAsignatura, Long idCurso, Long docente, Long usuario,
      Date fechaInicio, Date fechaFin);

}
