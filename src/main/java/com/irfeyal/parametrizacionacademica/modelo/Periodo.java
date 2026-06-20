package com.irfeyal.parametrizacionacademica.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_periodo")
  private Long idPeriodo;

  @Column(name = "ano_inicio")
  private String anoInicio;

  @Column(name = "ano_fin")
  private String anoFin;

  @ManyToOne
  @JoinColumn(name = "id_malla")
  private Malla malla;

  public Periodo() {
  }

  public Periodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  public Long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  public String getAnoInicio() {
    return anoInicio;
  }

  public void setAnoInicio(String anoInicio) {
    this.anoInicio = anoInicio;
  }

  public String getAnoFin() {
    return anoFin;
  }

  public void setAnoFin(String anoFin) {
    this.anoFin = anoFin;
  }

  public Malla getMalla() {
    return malla;
  }

  public void setMalla(Malla malla) {
    this.malla = malla;
  }

}
