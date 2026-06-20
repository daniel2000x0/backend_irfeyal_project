package com.irfeyal.asistencia.servicio;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irfeyal.asistencia.dao.IClaseDao;
import com.irfeyal.asistencia.dto.ClaseDTO;
import com.irfeyal.asistencia.mapper.ClaseMapper;
import com.irfeyal.asistencia.interfaces.IClaseService;
import com.irfeyal.asistencia.modelo.Clase;

/**
 * Implementación del servicio de Clase.
 * Usa ClaseMapper para convertir entre entidad y DTO.
 * La validación de clase duplicada se delega al DAO con consultas nativas.
 */
@Service
public class ClaseServiceImpl implements IClaseService {

  @Autowired
  private IClaseDao clasedao;

  @Autowired
  private ClaseMapper claseMapper;

  @Override
  public ClaseDTO save(ClaseDTO claseDTO) {
    Clase entity = claseMapper.toEntity(claseDTO);
    Clase saved = clasedao.save(entity);
    return claseMapper.toDto(saved);
  }

  @Override
  public List<ClaseDTO> findAll() {
    return clasedao.findAll().stream()
        .map(claseMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public ClaseDTO findById(Long id) {
    return clasedao.findById(id)
        .map(claseMapper::toDto)
        .orElse(null);
  }

  @Override
  public List<ClaseDTO> mostrarfechas(Long idEstudiante, Integer idDocente, Integer idAsignatura,
      Integer idCurso, Integer idParalelo, Integer idModalidad, Integer idPeriodo) {
    return clasedao.mostrarfechasid(idEstudiante, idDocente, idAsignatura,
        idCurso, idParalelo, idModalidad, idPeriodo).stream()
        .map(claseMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public ClaseDTO ultimoingreso() {
    return claseMapper.toDto(clasedao.findclaseingreseda());
  }

  @Override
  public List<ClaseDTO> validarclase(Integer idDocente, Integer idPeriodo, Integer idModalidad,
      Integer idCurso, Integer idParalelo, Integer idAsignatura, Date fecha) {
    return clasedao.validarclase(idDocente, idPeriodo, idModalidad,
        idCurso, idParalelo, idAsignatura, fecha).stream()
        .map(claseMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Long validarclass(Integer idDocente, Integer idPeriodo, Integer idModalidad,
      Integer idCurso, Integer idParalelo, Integer idAsignatura, Date fecha) {
    return clasedao.validarclass(idDocente, idPeriodo, idModalidad,
        idCurso, idParalelo, idAsignatura, fecha);
  }

}
