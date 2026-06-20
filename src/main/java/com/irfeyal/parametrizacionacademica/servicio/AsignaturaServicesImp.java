package com.irfeyal.parametrizacionacademica.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irfeyal.parametrizacionacademica.dao.AsignaturaRepository;
import com.irfeyal.parametrizacionacademica.interfaces.AsignaturaServices;
import com.irfeyal.parametrizacionacademica.modelo.Asignatura;

@Service
public class AsignaturaServicesImp implements AsignaturaServices {

  @Autowired
  private AsignaturaRepository asignaturaRepository;

  @Override
  public List<Asignatura> getAllAsignatura() {
    return asignaturaRepository.findAll();
  }

}
