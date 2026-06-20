package com.irfeyal.asistencia.dto;

import javax.validation.constraints.NotNull;

public class AsistenciaUpdateDTO {

    @NotNull(message = "El estado de asistencia es requerido")
    private Boolean estadoAsis;

    public AsistenciaUpdateDTO() {
    }

    public AsistenciaUpdateDTO(Boolean estadoAsis) {
        this.estadoAsis = estadoAsis;
    }

    public Boolean getEstadoAsis() {
        return estadoAsis;
    }

    public void setEstadoAsis(Boolean estadoAsis) {
        this.estadoAsis = estadoAsis;
    }
}
