package com.irfeyal.matricula.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.irfeyal.rolseguridad.modelo.Persona;

@Entity
@Table(name = "estudiantes")
public class Estudiante implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_estudiante")
  private Long idEstudiante;

  @Column(name = "estado_estudiante")
  private Boolean estadoEstudiante;

  @OneToOne
  @JoinColumn(name = "id_persona")
  private Persona idPersona;

  public Estudiante() {
    super();
  }

  public Estudiante(Long idEstudiante) {
    super();
    this.idEstudiante = idEstudiante;
  }

  public Long getIdEstudiante() {
    return idEstudiante;
  }

  public void setIdEstudiante(Long idEstudiante) {
    this.idEstudiante = idEstudiante;
  }

  public Boolean getEstadoEstudiante() {
    return estadoEstudiante;
  }

  public void setEstadoEstudiante(Boolean estadoEstudiante) {
    this.estadoEstudiante = estadoEstudiante;
  }

  public Persona getIdPersona() {
    return idPersona;
  }

  public void setIdPersona(Persona idPersona) {
    this.idPersona = idPersona;
  }

}
