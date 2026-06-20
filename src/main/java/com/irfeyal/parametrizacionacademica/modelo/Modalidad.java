package com.irfeyal.parametrizacionacademica.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "modalidad")
public class Modalidad implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_modalidad")
  private Long idModalidad;

  private String descripcion;

  public Modalidad() {
  }

  public Modalidad(Long idModalidad) {
    this.idModalidad = idModalidad;
  }

  public Long getIdModalidad() {
    return idModalidad;
  }

  public void setIdModalidad(Long idModalidad) {
    this.idModalidad = idModalidad;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
