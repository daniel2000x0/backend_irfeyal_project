package com.irfeyal.asistencia.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.irfeyal.parametrizacionacademica.modelo.Asignatura;
import com.irfeyal.parametrizacionacademica.modelo.Curso;
import com.irfeyal.parametrizacionacademica.modelo.Modalidad;
import com.irfeyal.parametrizacionacademica.modelo.Paralelo;
import com.irfeyal.parametrizacionacademica.modelo.Periodo;
import com.irfeyal.rolseguridad.modelo.Empleado;

@Entity
@Table(name = "clase")
public class Clase implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_clase")
  private Long idClase;

  @Column(name = "fec_clase")
  @Temporal(TemporalType.DATE)
  private Date fecClase;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClase")
  private Collection<Asistencia> asistenciaCollection;

  @JoinColumn(name = "id_curso", referencedColumnName = "id_curso", nullable = false)
  @ManyToOne(optional = false)
  private Curso idCurso;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_modalidad")
  private Modalidad idModalidad;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_periodo")
  private Periodo idPeriodo;

  @JoinColumn(name = "id_paralelo", referencedColumnName = "id_paralelo", nullable = false)
  @ManyToOne(optional = false)
  private Paralelo idParalelo;

  @JoinColumn(name = "id_asignatura", referencedColumnName = "id_asignatura", nullable = false)
  @ManyToOne(optional = false)
  private Asignatura idAsignatura;

  @JoinColumn(name = "id_docente", referencedColumnName = "id_empleado", nullable = false)
  @ManyToOne(optional = false)
  private Empleado idDocente;

  public Clase() {
  }

  public Clase(Long idClase) {
    this.idClase = idClase;
  }

  public Clase(Long idClase, Date fecClase) {
    this.idClase = idClase;
    this.fecClase = fecClase;
  }

  public Long getIdClase() {
    return idClase;
  }

  public void setIdClase(Long idClase) {
    this.idClase = idClase;
  }

  public Date getFecClase() {
    return fecClase;
  }

  public void setFecClase(Date fecClase) {
    this.fecClase = fecClase;
  }

  public Collection<Asistencia> getAsistenciaCollection() {
    return asistenciaCollection;
  }

  public void setAsistenciaCollection(Collection<Asistencia> asistenciaCollection) {
    this.asistenciaCollection = asistenciaCollection;
  }

  public Asignatura getIdAsignatura() {
    return idAsignatura;
  }

  public void setIdAsignatura(Asignatura idAsignatura) {
    this.idAsignatura = idAsignatura;
  }

  public Curso getIdCurso() {
    return idCurso;
  }

  public void setIdCurso(Curso idCurso) {
    this.idCurso = idCurso;
  }

  public Empleado getIdDocente() {
    return idDocente;
  }

  public void setIdDocente(Empleado idDocente) {
    this.idDocente = idDocente;
  }

  public Modalidad getIdModalidad() {
    return idModalidad;
  }

  public void setIdModalidad(Modalidad idModalidad) {
    this.idModalidad = idModalidad;
  }

  public Paralelo getIdParalelo() {
    return idParalelo;
  }

  public void setIdParalelo(Paralelo idParalelo) {
    this.idParalelo = idParalelo;
  }

  public Periodo getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Periodo idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idClase != null ? idClase.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Clase)) {
      return false;
    }
    Clase other = (Clase) object;
    if ((this.idClase == null && other.idClase != null)
        || (this.idClase != null && !this.idClase.equals(other.idClase))) {
      return false;
    }
    return true;
  }

  public String getDescripcion() {
    return this.idAsignatura != null ? this.idAsignatura.getDescripcion() : "----";
  }

}
