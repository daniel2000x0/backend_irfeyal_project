package com.irfeyal.rolseguridad.dao;

import org.springframework.data.repository.CrudRepository;

import com.irfeyal.rolseguridad.modelo.Empleado;

public interface EmpleadoDAO extends CrudRepository<Empleado, Long> {

}
