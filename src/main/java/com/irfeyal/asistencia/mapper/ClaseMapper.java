package com.irfeyal.asistencia.mapper;

import com.irfeyal.asistencia.dto.ClaseDTO;
import com.irfeyal.asistencia.modelo.Clase;
import com.irfeyal.parametrizacionacademica.modelo.Asignatura;
import com.irfeyal.parametrizacionacademica.modelo.Curso;
import com.irfeyal.parametrizacionacademica.modelo.Modalidad;
import com.irfeyal.parametrizacionacademica.modelo.Paralelo;
import com.irfeyal.parametrizacionacademica.modelo.Periodo;
import com.irfeyal.rolseguridad.modelo.Empleado;
import org.springframework.stereotype.Component;

@Component
public class ClaseMapper {

    public ClaseDTO toDto(Clase entity) {
        if (entity == null) return null;
        ClaseDTO dto = new ClaseDTO();
        dto.setIdClase(entity.getIdClase());
        dto.setFecClase(entity.getFecClase());
        dto.setIdCurso(entity.getIdCurso() != null ? entity.getIdCurso().getIdCurso() : null);
        dto.setIdModalidad(entity.getIdModalidad() != null ? entity.getIdModalidad().getIdModalidad() : null);
        dto.setIdPeriodo(entity.getIdPeriodo() != null ? entity.getIdPeriodo().getIdPeriodo() : null);
        dto.setIdParalelo(entity.getIdParalelo() != null ? entity.getIdParalelo().getIdParalelo() : null);
        dto.setIdAsignatura(entity.getIdAsignatura() != null ? entity.getIdAsignatura().getIdAsignatura() : null);
        dto.setIdDocente(entity.getIdDocente() != null ? entity.getIdDocente().getIdEmpleado() : null);
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }

    public Clase toEntity(ClaseDTO dto) {
        if (dto == null) return null;
        Clase entity = new Clase();
        entity.setIdClase(dto.getIdClase());
        entity.setFecClase(dto.getFecClase());
        if (dto.getIdCurso() != null) {
            entity.setIdCurso(new Curso(dto.getIdCurso()));
        }
        if (dto.getIdModalidad() != null) {
            entity.setIdModalidad(new Modalidad(dto.getIdModalidad()));
        }
        if (dto.getIdPeriodo() != null) {
            entity.setIdPeriodo(new Periodo(dto.getIdPeriodo()));
        }
        if (dto.getIdParalelo() != null) {
            entity.setIdParalelo(new Paralelo(dto.getIdParalelo()));
        }
        if (dto.getIdAsignatura() != null) {
            entity.setIdAsignatura(new Asignatura(dto.getIdAsignatura()));
        }
        if (dto.getIdDocente() != null) {
            entity.setIdDocente(new Empleado(dto.getIdDocente()));
        }
        return entity;
    }
}
