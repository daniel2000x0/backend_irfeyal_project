package com.irfeyal.asistencia.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.irfeyal.asistencia.modelo.Asistencia;

public interface IAsistenciaDao extends JpaRepository<Asistencia, Long> {

  @Query(value = "SELECT * "
      + "FROM Asistencia asis "
      + "join clase cls on cls.id_clase = asis.id_clase "
      + "WHERE cls.id_modalidad_id_modalidad=?1 and cls.id_periodo_id_periodo=?2 "
      + "and cls.id_paralelo=?3 and cls.id_asignatura=?4 and cls.id_curso=?5 and cls.fec_clase=?6",
      nativeQuery = true)
  List<Asistencia> buscarasistencia(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha);

  @Query(value = "SELECT * FROM asistencia asis join clase cls on cls.id_clase= asis.id_clase where "
      + "cls.id_modalidad_id_modalidad=?1 and cls.id_periodo_id_periodo=?2 "
      + "and cls.id_paralelo=?3 and cls.id_asignatura=?4 and cls.id_curso=?5 "
      + "and cls.fec_clase=?6 and cls.id_docente=?7", nativeQuery = true)
  List<Asistencia> actualizarfiltros(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha, Long idDocente);

  @Query(value = "SELECT * "
      + "FROM Asistencia asis "
      + "join clase c on c.id_clase = asis.id_clase "
      + "WHERE asis.id_estudiante = ?1 and c.id_asignatura=?3 and c.id_docente=?2 "
      + "and asis.estado_asis=true and c.fec_clase>=?4 and c.fec_clase<=?5 ",
      nativeQuery = true)
  List<Asistencia> obtenerIdEstudiante(Long idEstudiante, Long idDocente,
      Long idAsignatura, Date fechaInicio, Date fechaFin);

  @Query(value = "SELECT * "
      + "FROM Asistencia asis "
      + "join clase c on c.id_clase = asis.id_clase "
      + "WHERE asis.id_estudiante = ?1 and c.id_asignatura=?2 "
      + "and asis.estado_asis=true and c.fec_clase>=?3 and c.fec_clase<=?4",
      nativeQuery = true)
  List<Asistencia> obtenerIdEstudianteAdmin(Long idEstudiante, Long idAsignatura,
      Date fechaInicio, Date fechaFin);

  @Query(value = "SELECT * "
      + "FROM Asistencia asis "
      + "join clase cls on cls.id_clase = asis.id_clase "
      + "WHERE cls.id_modalidad_id_modalidad=?1 and cls.id_periodo_id_periodo=?2 "
      + "and cls.id_paralelo=?3 and cls.id_curso=?4 and cls.id_docente=?5 "
      + "and asis.estado_asis=true and cls.fec_clase>=?6 and cls.fec_clase<=?7",
      nativeQuery = true)
  List<Asistencia> sistenciapdf(Long idMod, Long idPeriodo, Long idParalelo,
      Long idCurso, Long docente, Date fechaInicio, Date fechaFin);

  @Query(value = "SELECT * "
      + "FROM Asistencia asis "
      + "join clase cls on cls.id_clase = asis.id_clase "
      + "WHERE cls.id_modalidad_id_modalidad=?1 and cls.id_periodo_id_periodo=?2 "
      + "and cls.id_paralelo=?3 and cls.id_curso=?4 "
      + "and asis.estado_asis=true and cls.fec_clase>=?5 and cls.fec_clase<=?6",
      nativeQuery = true)
  List<Asistencia> sistenciapdftutor(Long idMod, Long idPeriodo, Long idParalelo,
      Long idCurso, Date fechaInicio, Date fechaFin);

}
