package com.irfeyal.asistencia.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.irfeyal.asistencia.dao.IAsistenciaDao;
import com.irfeyal.asistencia.dao.IClaseDao;
import com.irfeyal.asistencia.dto.AsistenciaDTO;
import com.irfeyal.asistencia.mapper.AsistenciaMapper;
import com.irfeyal.asistencia.modelo.Asistencia;
import com.irfeyal.asistencia.modelo.Clase;
import com.irfeyal.matricula.dao.IEstudianteDao;
import com.irfeyal.matricula.modelo.Estudiante;
import com.irfeyal.parametrizacionacademica.dao.AsignaturaRepository;
import com.irfeyal.parametrizacionacademica.dao.CursoRepository;
import com.irfeyal.parametrizacionacademica.dao.ModalidadRepository;
import com.irfeyal.parametrizacionacademica.dao.ParaleloRepository;
import com.irfeyal.parametrizacionacademica.dao.PeriodoRepository;
import com.irfeyal.rolseguridad.dao.EmpleadoDAO;
import com.irfeyal.rolseguridad.dao.PersonaDAO;
import com.irfeyal.rolseguridad.dao.RolUsuarioDAO;

@ExtendWith(MockitoExtension.class)
class AsistenciaServiceTest {

    @Mock private IAsistenciaDao asistenciaDao;
    @Mock private IClaseDao claseDao;
    @Mock private IEstudianteDao estudianteDao;
    @Mock private PersonaDAO personaDao;
    @Mock private AsignaturaRepository asignaturaRepository;
    @Mock private CursoRepository cursoRepository;
    @Mock private ModalidadRepository modalidadRepository;
    @Mock private ParaleloRepository paraleloRepository;
    @Mock private PeriodoRepository periodoRepository;
    @Mock private EmpleadoDAO empleadoDAO;
    @Mock private RolUsuarioDAO rolUsuarioDAO;
    @Spy private AsistenciaMapper asistenciaMapper;

    @InjectMocks
    private AsistenciaServiceImpl asistenciaService;

    @Test
    void findById_shouldReturnDtoWhenExists() {
        Clase clase = new Clase(1L);
        Estudiante estudiante = new Estudiante(1L);
        Asistencia entity = new Asistencia(1L, true, clase, estudiante);

        when(asistenciaDao.findById(1L)).thenReturn(Optional.of(entity));

        AsistenciaDTO result = asistenciaService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdAsistencia());
        assertTrue(result.getEstadoAsis());
    }

    @Test
    void findById_shouldReturnNullWhenNotExists() {
        when(asistenciaDao.findById(99L)).thenReturn(Optional.empty());

        AsistenciaDTO result = asistenciaService.findById(99L);

        assertNull(result);
    }

    @Test
    void findAll_withPageable_shouldReturnPage() {
        PageRequest pageable = PageRequest.of(0, 10);
        Clase clase = new Clase(1L);
        Estudiante estudiante = new Estudiante(1L);
        Asistencia entity = new Asistencia(1L, true, clase, estudiante);
        Page<Asistencia> page = new PageImpl<>(List.of(entity));

        when(asistenciaDao.findAll(pageable)).thenReturn(page);

        Page<AsistenciaDTO> result = asistenciaService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getIdAsistencia());
    }

    @Test
    void save_shouldReturnSavedDto() {
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setEstadoAsis(true);
        dto.setIdClase(1L);
        dto.setIdEstudiante(1L);

        Clase clase = new Clase(1L);
        Estudiante estudiante = new Estudiante(1L);
        Asistencia entityToSave = new Asistencia(null, true, clase, estudiante);
        Asistencia savedEntity = new Asistencia(1L, true, clase, estudiante);

        when(asistenciaDao.save(any(Asistencia.class))).thenReturn(savedEntity);

        AsistenciaDTO result = asistenciaService.save(dto);

        assertNotNull(result);
        assertEquals(1L, result.getIdAsistencia());
        assertTrue(result.getEstadoAsis());
    }

    @Test
    void delete_shouldCallDao() {
        asistenciaService.delete(1L);
        verify(asistenciaDao).deleteById(1L);
    }

}
