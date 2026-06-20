package com.irfeyal.asistencia.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.irfeyal.asistencia.dto.ApiResponse;
import com.irfeyal.asistencia.dto.AsistenciaDTO;
import com.irfeyal.asistencia.dto.AsistenciaUpdateDTO;
import com.irfeyal.asistencia.dto.ClaseDTO;
import com.irfeyal.asistencia.interfaces.IAsistenciaService;
import com.irfeyal.asistencia.interfaces.IClaseService;
import com.irfeyal.matricula.interfaces.IEstudianteService;
import com.irfeyal.matricula.modelo.Estudiante;
import com.irfeyal.parametrizacionacademica.modelo.Asignatura;
import com.irfeyal.parametrizacionacademica.modelo.Curso;
import com.irfeyal.parametrizacionacademica.modelo.Modalidad;
import com.irfeyal.parametrizacionacademica.modelo.Paralelo;
import com.irfeyal.parametrizacionacademica.modelo.Periodo;
import com.irfeyal.rolseguridad.modelo.Persona;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/asistencia")
@Tag(name = "Asistencia", description = "CRUD de asistencias, clases, filtros y reportes PDF")
public class AsistenciaController {

    @Autowired
    private IAsistenciaService asistenciaService;

  @Autowired
  private IClaseService claseService;

  @Autowired
  private IEstudianteService estudianteService;

    @GetMapping("/asist/{id}")
    @Operation(summary = "Buscar estudiante por ID")
    public ResponseEntity<ApiResponse<Estudiante>> mostrarEstudiante(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.findById(id);
        if (estudiante == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.notFound("El id del estudiante " + id + " no existe"));
        }
        return ResponseEntity.ok(ApiResponse.ok(estudiante));
    }

    @GetMapping("/listarasistencia")
    @Operation(summary = "Listar asistencias con paginación")
    public ResponseEntity<ApiResponse<Page<AsistenciaDTO>>> listarAsistencia(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.findAll(pageable)));
    }

    @GetMapping("/asignaturas")
    @Operation(summary = "Listar todas las asignaturas")
    public ResponseEntity<ApiResponse<List<Asignatura>>> listarAsignaturas() {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.findAllAsignaturas()));
    }

    @GetMapping("/Modalidad")
    @Operation(summary = "Listar modalidades")
    public ResponseEntity<ApiResponse<List<Modalidad>>> listarModalidad() {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.findAllModalidad()));
    }

    @GetMapping("/Periodo")
    @Operation(summary = "Listar periodos")
    public ResponseEntity<ApiResponse<List<Periodo>>> listarPeriodo() {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.findAllPeriodo()));
    }

    @GetMapping("/Periodos/{idEmpleado}")
    @Operation(summary = "Listar periodos por empleado")
    public ResponseEntity<ApiResponse<List<Periodo>>> listarPeriodos(@PathVariable Long idEmpleado) {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.listarPeriodo(idEmpleado)));
    }

    @GetMapping("/modalidades/{idEmpleado}/{periodo}")
    @Operation(summary = "Listar modalidades por empleado y periodo")
    public ResponseEntity<ApiResponse<List<Modalidad>>> listarModalidades(@PathVariable Long idEmpleado,
            @PathVariable Long periodo) {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.listarModalidad(idEmpleado, periodo)));
    }

    @GetMapping("/cursos/{idEmpleado}/{periodo}/{idMod}")
    @Operation(summary = "Listar cursos por empleado, periodo y modalidad")
    public ResponseEntity<ApiResponse<List<Curso>>> listarCurso(@PathVariable Long idEmpleado,
            @PathVariable Long periodo, @PathVariable Long idMod) {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.listarCurso(idEmpleado, periodo, idMod)));
    }

    @GetMapping("/paralelos/{idEmpleado}/{periodo}/{modalidad}/{idCurso}")
    @Operation(summary = "Listar paralelos")
    public ResponseEntity<ApiResponse<List<Paralelo>>> listarParalelo(@PathVariable Long idEmpleado,
            @PathVariable Long periodo, @PathVariable Long modalidad, @PathVariable Long idCurso) {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.listarParalelo(idEmpleado, periodo, modalidad, idCurso)));
    }

    @GetMapping("/asignaturas/{empleado}/{idPeriodo}/{modalidad}/{idCurso}/{idParalelo}")
    @Operation(summary = "Listar asignaturas por filtros")
    public ResponseEntity<ApiResponse<List<Asignatura>>> listarAsignatura(@PathVariable Long empleado,
            @PathVariable Long idPeriodo, @PathVariable Long modalidad,
            @PathVariable Long idCurso, @PathVariable Long idParalelo) {
        return ResponseEntity.ok(ApiResponse.ok(
            asistenciaService.listarAsignatura(empleado, idPeriodo, modalidad, idCurso, idParalelo)));
    }

    @GetMapping("/Curso")
    @Operation(summary = "Listar todos los cursos")
    public ResponseEntity<ApiResponse<List<Curso>>> listarTodosCurso() {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.findAllCurso()));
    }

    @GetMapping("/Paralelo")
    @Operation(summary = "Listar todos los paralelos")
    public ResponseEntity<ApiResponse<List<Paralelo>>> listarTodosParalelo() {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.findAllParalelo()));
    }

    @PostMapping("/asistenciasave")
    @Operation(summary = "Crear una asistencia")
    public ResponseEntity<ApiResponse<AsistenciaDTO>> save(@RequestBody AsistenciaDTO asistenciaDTO) {
        AsistenciaDTO creada = asistenciaService.save(asistenciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(creada));
    }

    @GetMapping("/claseingresada")
    @Operation(summary = "Obtener la última clase ingresada")
    public ResponseEntity<ApiResponse<ClaseDTO>> claseIngresada() {
        return ResponseEntity.ok(ApiResponse.ok(claseService.ultimoingreso()));
    }

    @PostMapping("/clasesave")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una clase")
    public ResponseEntity<ApiResponse<ClaseDTO>> createClase(@Valid @RequestBody ClaseDTO claseDTO) {
        ClaseDTO creada = claseService.save(claseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(creada));
    }

    @GetMapping("/listarclase")
    @Operation(summary = "Listar clases")
    public ResponseEntity<ApiResponse<List<ClaseDTO>>> listarClase() {
        return ResponseEntity.ok(ApiResponse.ok(claseService.findAll()));
    }

    @DeleteMapping("/asistenciadelete/{id}")
    @Operation(summary = "Eliminar una asistencia")
    public ResponseEntity<ApiResponse<AsistenciaDTO>> delete(@PathVariable Long id) {
        AsistenciaDTO asistencia = asistenciaService.findById(id);
        if (asistencia == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.notFound("La asistencia con ID " + id + " no existe"));
        }
        asistenciaService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(asistencia));
    }

    @GetMapping("/buscarestudiantesdeuncurso/{id}")
    @Operation(summary = "Buscar estudiantes de un curso")
    public ResponseEntity<ApiResponse<List<Persona>>> buscarCurso(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.buscarCurso(id)));
    }

    @GetMapping("/filtrosdelaasistencia/{idMod}/{idPer}/{idPar}/{idAsig}/{idCurs}")
    @Operation(summary = "Filtrar estudiantes por modalidad, periodo, paralelo, asignatura, curso")
    public ResponseEntity<ApiResponse<List<Estudiante>>> filtros(@PathVariable Long idMod,
            @PathVariable Long idPer, @PathVariable Long idPar, @PathVariable Long idAsig,
            @PathVariable Long idCurs) {
        return ResponseEntity.ok(ApiResponse.ok(
            asistenciaService.buscarCursoModalidad(idMod, idPer, idPar, idAsig, idCurs)));
    }

    @GetMapping("/buscarestudianteporcedula/{ced}")
    @Operation(summary = "Buscar estudiante por cédula")
    public ResponseEntity<ApiResponse<Estudiante>> buscarPorCedula(@PathVariable String ced) {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.buscarCedulaEstudiante(ced)));
    }

    @GetMapping("/buscarestudianteid/{id}")
    @Operation(summary = "Buscar información de estudiante por ID")
    public ResponseEntity<ApiResponse<List<Estudiante>>> buscarEstudiantePorId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(asistenciaService.mostrarInformacion(id)));
    }

    @GetMapping("/buscaractualizar/{idMod}/{idPeriodo}/{idParalelo}/{idAsignatura}/{idCurso}/{fecha}/{docente}")
    @Operation(summary = "Buscar asistencias para actualizar")
    public ResponseEntity<ApiResponse<List<AsistenciaDTO>>> buscarActualizar(@PathVariable Long idMod,
            @PathVariable Long idPeriodo, @PathVariable Long idParalelo,
            @PathVariable Long idAsignatura, @PathVariable Long idCurso,
            @PathVariable String fecha, @PathVariable Long docente) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date auxFecha = formato.parse(fecha);
        List<AsistenciaDTO> result = asistenciaService.buscarAsistencia(
            idMod, idPeriodo, idParalelo, idAsignatura, idCurso, auxFecha, docente);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PutMapping("/updateasistencia/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Actualizar estado de una asistencia")
    public ResponseEntity<ApiResponse<AsistenciaDTO>> update(
            @Valid @RequestBody AsistenciaUpdateDTO updateDto, @PathVariable Long id) {
        AsistenciaDTO asistenciaActual = asistenciaService.findById(id);
        if (asistenciaActual == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.notFound("Error: no se pudo editar, la asistencia ID: " + id + " no existe"));
        }
        asistenciaActual.setEstadoAsis(updateDto.getEstadoAsis());
        AsistenciaDTO actualizada = asistenciaService.save(asistenciaActual);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(actualizada));
    }

    @PutMapping("/claseactualizar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Actualizar una clase")
    public ResponseEntity<ApiResponse<ClaseDTO>> actualizarClase(@RequestBody ClaseDTO claseDTO,
            @PathVariable Long id) {
        claseDTO.setIdClase(id);
        ClaseDTO actualizada = claseService.save(claseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(actualizada));
    }

    @GetMapping("/mostrarfechasdefaltas/{ides}/{iddo}/{idasig}/{idcur}/{idpar}/{idmod}/{idperi}")
    @Operation(summary = "Mostrar fechas de faltas de un estudiante")
    public ResponseEntity<ApiResponse<List<ClaseDTO>>> mostrarFechasDeFaltas(@PathVariable Long ides,
            @PathVariable Integer iddo, @PathVariable Integer idasig, @PathVariable Integer idcur,
            @PathVariable Integer idpar, @PathVariable Integer idmod, @PathVariable Integer idperi) {
        return ResponseEntity.ok(ApiResponse.ok(
            claseService.mostrarfechas(ides, iddo, idasig, idcur, idpar, idmod, idperi)));
    }

    @GetMapping("/buscarAsistencia/{idMod}/{idPeriodo}/{idParalelo}/{idAsignatura}/{idCurso}/{fecha}/{docente}")
    @Operation(summary = "Buscar asistencias por filtros completos")
    public ResponseEntity<ApiResponse<List<AsistenciaDTO>>> buscarAsistencia(@PathVariable Long idMod,
            @PathVariable Long idPeriodo, @PathVariable Long idParalelo,
            @PathVariable Long idAsignatura, @PathVariable Long idCurso,
            @PathVariable String fecha, @PathVariable Long docente) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date auxFecha = formato.parse(fecha);
        List<AsistenciaDTO> result = asistenciaService.buscarAsistencia(
            idMod, idPeriodo, idParalelo, idAsignatura, idCurso, auxFecha, docente);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/validarclass/{idDoc}/{idPeriodo}/{idMod}/{idCurso}/{idParalelo}/{idAsignatura}/{fechac}")
    @Operation(summary = "Validar si existe una clase duplicada")
    public ResponseEntity<ApiResponse<Long>> validarClass(@PathVariable Integer idDoc,
            @PathVariable Integer idPeriodo, @PathVariable Integer idMod,
            @PathVariable Integer idCurso, @PathVariable Integer idParalelo,
            @PathVariable Integer idAsignatura, @PathVariable String fechac) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date auxFecha = formato.parse(fechac);
        Long count = claseService.validarclass(idDoc, idPeriodo, idMod, idCurso,
            idParalelo, idAsignatura, auxFecha);
        return ResponseEntity.ok(ApiResponse.ok(count));
    }

    @GetMapping("/exportInvoice/{idEstudiante}/{idDocente}/{idAsignatura}/{usuario}/{fechaInicio}/{fechaFin}")
    @Operation(summary = "Exportar reporte PDF de asistencia por estudiante")
    public ResponseEntity<ByteArrayResource> exportInvoice(@PathVariable Long idEstudiante,
            @PathVariable Long idDocente, @PathVariable Long idAsignatura, @PathVariable Long usuario,
            @PathVariable Date fechaInicio, @PathVariable Date fechaFin) {
        return asistenciaService.exportInvoice(idEstudiante, idDocente, idAsignatura,
            usuario, fechaInicio, fechaFin);
    }

    @GetMapping("/exportInvoicecurso/{idMod}/{idPeriodo}/{idParalelo}/{idAsignatura}/{idCurso}/{docente}/{usuario}/{fechaInicio}/{fechaFin}")
    @Operation(summary = "Exportar reporte PDF de asistencia por curso")
    public ResponseEntity<ByteArrayResource> exportInvoiceCurso(@PathVariable Long idMod,
            @PathVariable Long idPeriodo, @PathVariable Long idParalelo,
            @PathVariable Long idAsignatura, @PathVariable Long idCurso,
            @PathVariable Long docente, @PathVariable Long usuario,
            @PathVariable Date fechaInicio, @PathVariable Date fechaFin) {
        return asistenciaService.exportInvoiceCurso(idMod, idPeriodo, idParalelo,
            idAsignatura, idCurso, docente, usuario, fechaInicio, fechaFin);
    }

}
