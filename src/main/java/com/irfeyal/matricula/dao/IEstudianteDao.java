package com.irfeyal.matricula.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.irfeyal.matricula.modelo.Estudiante;

public interface IEstudianteDao extends CrudRepository<Estudiante, Long> {

  @Query(value = "SELECT * FROM estudiantes e "
      + "JOIN persona p ON p.id_persona = e.id_persona "
      + "JOIN matricula m ON m.id_estudiante = e.id_estudiante "
      + "JOIN curso c ON c.id_curso = m.id_curso "
      + "JOIN paralelo pj ON pj.id_paralelo = m.id_paralelo "
      + "WHERE m.id_modalidad = ?1 AND m.id_periodo = ?2 "
      + "AND m.id_paralelo = ?3 AND m.id_asignatura = ?4 AND m.id_curso = ?5",
      nativeQuery = true)
  List<Estudiante> buscarcursotodofil(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso);

  @Query(value = "SELECT * FROM estudiantes e "
      + "JOIN persona p ON p.id_persona = e.id_persona "
      + "WHERE p.cedula = ?1", nativeQuery = true)
  Estudiante buscarcedulaestudiante(String cedula);

  @Query(value = "SELECT * FROM estudiantes WHERE id_estudiante = ?1", nativeQuery = true)
  List<Estudiante> findestudianteid(Long idEstudiante);

  @Query(value = "SELECT * FROM estudiantes WHERE id_estudiante = ?1", nativeQuery = true)
  Estudiante findestudianteidpdf(Long idEstudiante);

}
