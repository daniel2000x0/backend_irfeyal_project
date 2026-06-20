package com.irfeyal.parametrizacionacademica.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "malla")
public class Malla implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_malla")
  private Long idMalla;

  private String descripcion;

  public Malla() {
  }

  public Long getIdMalla() {
    return idMalla;
  }

  public void setIdMalla(Long idMalla) {
    this.idMalla = idMalla;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
