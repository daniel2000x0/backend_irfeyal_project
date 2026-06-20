package com.irfeyal.asistencia.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.irfeyal.asistencia.dto.AsistenciaDTO;
import com.irfeyal.asistencia.modelo.Asistencia;
import com.irfeyal.asistencia.modelo.Clase;
import com.irfeyal.matricula.modelo.Estudiante;
import com.irfeyal.rolseguridad.modelo.Persona;

class AsistenciaMapperTest {

    private final AsistenciaMapper mapper = new AsistenciaMapper();

    @Test
    void toDto_shouldMapAllFields() {
        Persona persona = new Persona();
        persona.setIdPersona(1L);
        persona.setNombre("Juan");
        persona.setApellido("Pérez");
        persona.setCedula("1234567890");

        Estudiante estudiante = new Estudiante();
        estudiante.setIdEstudiante(10L);
        estudiante.setIdPersona(persona);

        Clase clase = new Clase();
        clase.setIdClase(20L);
        clase.setFecClase(new Date());

        Asistencia entity = new Asistencia();
        entity.setIdAsistencia(1L);
        entity.setEstadoAsis(true);
        entity.setIdClase(clase);
        entity.setIdEstudiante(estudiante);

        AsistenciaDTO dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getIdAsistencia());
        assertTrue(dto.getEstadoAsis());
        assertEquals(20L, dto.getIdClase());
        assertEquals(10L, dto.getIdEstudiante());
        assertEquals("Juan", dto.getNombre());
        assertEquals("Pérez", dto.getApellido());
        assertEquals("1234567890", dto.getCedula());
    }

    @Test
    void toDto_shouldReturnNullForNullEntity() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toEntity_shouldMapAllFields() {
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setIdAsistencia(1L);
        dto.setEstadoAsis(true);
        dto.setIdClase(20L);
        dto.setIdEstudiante(10L);

        Asistencia entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getIdAsistencia());
        assertTrue(entity.getEstadoAsis());
        assertNotNull(entity.getIdClase());
        assertEquals(20L, entity.getIdClase().getIdClase());
        assertNotNull(entity.getIdEstudiante());
        assertEquals(10L, entity.getIdEstudiante().getIdEstudiante());
    }

    @Test
    void toEntity_shouldReturnNullForNullDto() {
        assertNull(mapper.toEntity(null));
    }

}
