package com.irfeyal.rolseguridad.modelo;

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
@Table(name = "rol_usuario")
public class RolUsuario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_rol_usuario")
  private Long idRolUsuario;

  private Boolean estado;

  @ManyToOne
  @JoinColumn(name = "id_rol")
  private Rol rol;

  @ManyToOne
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;

  public RolUsuario() {
  }

  public Long getIdRolUsuario() {
    return idRolUsuario;
  }

  public void setIdRolUsuario(Long idRolUsuario) {
    this.idRolUsuario = idRolUsuario;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

}
