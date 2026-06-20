package com.irfeyal.asistencia.servicio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irfeyal.asistencia.dao.IClaseDao;
import com.irfeyal.asistencia.interfaces.IClaseService;
import com.irfeyal.asistencia.modelo.Clase;

@Service
public class ClaseServiceImpl implements IClaseService {

  @Autowired
  private IClaseDao clasedao;

  @Override
  public Clase save(Clase clase) {
    return clasedao.save(clase);
  }

  @Override
  public List<Clase> findAll() {
    return clasedao.findAll();
  }

  @Override
  public Clase findById(Long id) {
    return clasedao.getById(id);
  }

  @Override
  public List<Clase> mostrarfechas(Long idEstudiante, Integer idDocente, Integer idAsignatura,
      Integer idCurso, Integer idParalelo, Integer idModalidad, Integer idPeriodo) {
    return clasedao.mostrarfechasid(idEstudiante, idDocente, idAsignatura,
        idCurso, idParalelo, idModalidad, idPeriodo);
  }

  @Override
  public Clase ultimoingreso() {
    return clasedao.findclaseingreseda();
  }

  @Override
  public List<Clase> validarclase(Integer idDocente, Integer idPeriodo, Integer idModalidad,
      Integer idCurso, Integer idParalelo, Integer idAsignatura, Date fecha) {
    return clasedao.validarclase(idDocente, idPeriodo, idModalidad,
        idCurso, idParalelo, idAsignatura, fecha);
  }

  @Override
  public Long validarclass(Integer idDocente, Integer idPeriodo, Integer idModalidad,
      Integer idCurso, Integer idParalelo, Integer idAsignatura, Date fecha) {
    return clasedao.validarclass(idDocente, idPeriodo, idModalidad,
        idCurso, idParalelo, idAsignatura, fecha);
  }

}
