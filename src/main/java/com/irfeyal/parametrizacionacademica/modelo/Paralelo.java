package com.irfeyal.parametrizacionacademica.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paralelo")
public class Paralelo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_paralelo")
  private Long idParalelo;

  private String descripcion;

  public Paralelo() {
  }

  public Paralelo(Long idParalelo) {
    this.idParalelo = idParalelo;
  }

  public Long getIdParalelo() {
    return idParalelo;
  }

  public void setIdParalelo(Long idParalelo) {
    this.idParalelo = idParalelo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
