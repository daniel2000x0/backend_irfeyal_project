package com.irfeyal.asistencia.controlador;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.irfeyal.asistencia.dto.AsistenciaDTO;
import com.irfeyal.asistencia.interfaces.IAsistenciaService;
import com.irfeyal.asistencia.interfaces.IClaseService;
import com.irfeyal.asistencia.security.CustomUserDetailsService;
import com.irfeyal.asistencia.security.JwtAuthenticationFilter;
import com.irfeyal.matricula.interfaces.IEstudianteService;
import com.irfeyal.matricula.modelo.Estudiante;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = AsistenciaController.class,
    excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
    })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc(addFilters = false)
class AsistenciaControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private IAsistenciaService asistenciaService;
    @MockBean private IClaseService claseService;
    @MockBean private IEstudianteService estudianteService;
    @MockBean private CustomUserDetailsService customUserDetailsService;
    @MockBean private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void listarAsistencia_shouldReturnPage() throws Exception {
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setIdAsistencia(1L);
        dto.setEstadoAsis(true);
        Page<AsistenciaDTO> page = new PageImpl<>(List.of(dto));

        when(asistenciaService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/asistencia/listarasistencia")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data.content[0].idAsistencia").value(1));
    }

    @Test
    void mostrarEstudiante_whenExists_shouldReturnOk() throws Exception {
        Estudiante estudiante = new Estudiante(1L);
        when(estudianteService.findById(1L)).thenReturn(estudiante);

        mockMvc.perform(get("/asistencia/asist/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void mostrarEstudiante_whenNotExists_shouldReturn404() throws Exception {
        when(estudianteService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/asistencia/asist/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void listarAsignaturas_shouldReturnList() throws Exception {
        when(asistenciaService.findAllAsignaturas()).thenReturn(List.of());

        mockMvc.perform(get("/asistencia/asignaturas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void save_shouldReturnCreated() throws Exception {
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setEstadoAsis(true);
        dto.setIdClase(1L);
        dto.setIdEstudiante(1L);

        AsistenciaDTO saved = new AsistenciaDTO();
        saved.setIdAsistencia(1L);
        saved.setEstadoAsis(true);

        when(asistenciaService.save(any(AsistenciaDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/asistencia/asistenciasave")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value(201));
    }

    @Test
    void delete_whenExists_shouldReturnOk() throws Exception {
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setIdAsistencia(1L);
        when(asistenciaService.findById(1L)).thenReturn(dto);

        mockMvc.perform(delete("/asistencia/asistenciadelete/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void delete_whenNotExists_shouldReturn404() throws Exception {
        when(asistenciaService.findById(99L)).thenReturn(null);

        mockMvc.perform(delete("/asistencia/asistenciadelete/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404));
    }

}
