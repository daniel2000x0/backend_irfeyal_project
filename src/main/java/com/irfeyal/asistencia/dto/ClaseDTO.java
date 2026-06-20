package com.irfeyal.asistencia.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DTO de Clase. Campos obligatorios: fecClase, idCurso, idParalelo,
 * idAsignatura, idDocente. descripcion se popula automáticamente
 * desde la asignatura relacionada.
 */
public class ClaseDTO {

    private Long idClase;

    @NotNull(message = "La fecha de clase es requerida")
    private Date fecClase;

    @NotNull(message = "El ID del curso es requerido")
    private Long idCurso;

    private Long idModalidad;

    private Long idPeriodo;

    @NotNull(message = "El ID del paralelo es requerido")
    private Long idParalelo;

    @NotNull(message = "El ID de la asignatura es requerido")
    private Long idAsignatura;

    @NotNull(message = "El ID del docente es requerido")
    private Long idDocente;

    private String descripcion;

    public ClaseDTO() {
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

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Long getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(Long idModalidad) {
        this.idModalidad = idModalidad;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Long getIdParalelo() {
        return idParalelo;
    }

    public void setIdParalelo(Long idParalelo) {
        this.idParalelo = idParalelo;
    }

    public Long getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Long idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
