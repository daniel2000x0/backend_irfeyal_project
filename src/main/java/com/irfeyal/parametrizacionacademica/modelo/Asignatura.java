package com.irfeyal.parametrizacionacademica.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asignatura")
public class Asignatura implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_asignatura")
  private Long idAsignatura;

  private String descripcion;

  public Asignatura() {
  }

  public Asignatura(Long idAsignatura) {
    this.idAsignatura = idAsignatura;
  }

  public Long getIdAsignatura() {
    return idAsignatura;
  }

  public void setIdAsignatura(Long idAsignatura) {
    this.idAsignatura = idAsignatura;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
