package com.irfeyal.matricula.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irfeyal.matricula.dao.IEstudianteDao;
import com.irfeyal.matricula.interfaces.IEstudianteService;
import com.irfeyal.matricula.modelo.Estudiante;

@Service
public class EstudianteServiceImpl implements IEstudianteService {

  @Autowired
  private IEstudianteDao estudianteDao;

  @Override
  public Estudiante findById(Long id) {
    return estudianteDao.findById(id).orElse(null);
  }

}
