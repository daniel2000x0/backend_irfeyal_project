package com.irfeyal.asistencia.mapper;

import com.irfeyal.asistencia.dto.AsistenciaDTO;
import com.irfeyal.asistencia.modelo.Asistencia;
import com.irfeyal.asistencia.modelo.Clase;
import com.irfeyal.matricula.modelo.Estudiante;
import org.springframework.stereotype.Component;

@Component
public class AsistenciaMapper {

    public AsistenciaDTO toDto(Asistencia entity) {
        if (entity == null) return null;
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setIdAsistencia(entity.getIdAsistencia());
        dto.setEstadoAsis(entity.getEstadoAsis());
        dto.setIdClase(entity.getIdClase() != null ? entity.getIdClase().getIdClase() : null);
        dto.setIdEstudiante(entity.getIdEstudiante() != null ? entity.getIdEstudiante().getIdEstudiante() : null);
        if (entity.getIdEstudiante() != null && entity.getIdEstudiante().getIdPersona() != null) {
            dto.setNombre(entity.getIdEstudiante().getIdPersona().getNombre());
            dto.setApellido(entity.getIdEstudiante().getIdPersona().getApellido());
            dto.setCedula(entity.getIdEstudiante().getIdPersona().getCedula());
        }
        if (entity.getIdClase() != null) {
            dto.setDescripcion(entity.getIdClase().getDescripcion());
            dto.setFecClase(entity.getIdClase().getFecClase());
            if (entity.getIdClase().getIdAsignatura() != null) {
                dto.setDescripciones(entity.getIdClase().getIdAsignatura().getDescripcion());
            }
        }
        return dto;
    }

    public Asistencia toEntity(AsistenciaDTO dto) {
        if (dto == null) return null;
        Asistencia entity = new Asistencia();
        entity.setIdAsistencia(dto.getIdAsistencia());
        entity.setEstadoAsis(dto.getEstadoAsis());
        if (dto.getIdClase() != null) {
            entity.setIdClase(new Clase(dto.getIdClase()));
        }
        if (dto.getIdEstudiante() != null) {
            entity.setIdEstudiante(new Estudiante(dto.getIdEstudiante()));
        }
        return entity;
    }
}
