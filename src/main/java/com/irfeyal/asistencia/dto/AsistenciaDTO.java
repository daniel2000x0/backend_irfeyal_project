package com.irfeyal.asistencia.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DTO de Asistencia. Campos obligatorios para crear: estadoAsis, idClase, idEstudiante.
 * Campos de solo lectura (nombre, apellido, cedula, descripcion, fecClase,
 * descripciones) se poblan automáticamente en respuestas GET.
 */
public class AsistenciaDTO {

    private Long idAsistencia;

    @NotNull(message = "El estado de asistencia es requerido")
    private Boolean estadoAsis;

    @NotNull(message = "El ID de la clase es requerido")
    private Long idClase;

    @NotNull(message = "El ID del estudiante es requerido")
    private Long idEstudiante;

    private String nombre;

    private String apellido;

    private String cedula;

    private String descripcion;

    private Date fecClase;

    private String descripciones;

    public AsistenciaDTO() {
    }

    public Long getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(Long idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Boolean getEstadoAsis() {
        return estadoAsis;
    }

    public void setEstadoAsis(Boolean estadoAsis) {
        this.estadoAsis = estadoAsis;
    }

    public Long getIdClase() {
        return idClase;
    }

    public void setIdClase(Long idClase) {
        this.idClase = idClase;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecClase() {
        return fecClase;
    }

    public void setFecClase(Date fecClase) {
        this.fecClase = fecClase;
    }

    public String getDescripciones() {
        return descripciones;
    }

    public void setDescripciones(String descripciones) {
        this.descripciones = descripciones;
    }
}
