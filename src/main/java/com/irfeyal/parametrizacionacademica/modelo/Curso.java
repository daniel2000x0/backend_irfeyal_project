package com.irfeyal.parametrizacionacademica.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "curso")
public class Curso implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_curso")
  private Long idCurso;

  private String descripcion;

  public Curso() {
  }

  public Curso(Long idCurso) {
    this.idCurso = idCurso;
  }

  public Long getIdCurso() {
    return idCurso;
  }

  public void setIdCurso(Long idCurso) {
    this.idCurso = idCurso;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
