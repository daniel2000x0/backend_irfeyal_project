package com.irfeyal.asistencia.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.irfeyal.asistencia.interfaces.IAsistenciaService;
import com.irfeyal.asistencia.interfaces.IClaseService;
import com.irfeyal.asistencia.modelo.Asistencia;
import com.irfeyal.asistencia.modelo.Clase;
import com.irfeyal.matricula.interfaces.IEstudianteService;
import com.irfeyal.matricula.modelo.Estudiante;
import com.irfeyal.parametrizacionacademica.interfaces.AsignaturaServices;
import com.irfeyal.parametrizacionacademica.interfaces.PeriodoServices;
import com.irfeyal.parametrizacionacademica.modelo.Asignatura;
import com.irfeyal.parametrizacionacademica.modelo.Curso;
import com.irfeyal.parametrizacionacademica.modelo.Modalidad;
import com.irfeyal.parametrizacionacademica.modelo.Paralelo;
import com.irfeyal.parametrizacionacademica.modelo.Periodo;
import com.irfeyal.rolseguridad.modelo.Persona;

@RestController
@RequestMapping("/asistencia")
@CrossOrigin(origins = "*")
public class AsistenciaController {

  @Autowired
  private IAsistenciaService asistenciaservice;

  @Autowired
  private IClaseService claseservice;

  @Autowired
  private AsignaturaServices asignaturaservice;

  @Autowired
  private PeriodoServices periodoService;

  @Autowired
  private IEstudianteService estudianteService;

  @GetMapping("/asist/{id}")
  public ResponseEntity<?> showe(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>();
    Estudiante estudiante;
    try {
      estudiante = estudianteService.findById(id);
    } catch (Exception e) {
      response.put("mensaje", "Error al realizar consulta");
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    if (estudiante == null) {
      response.put("mensaje", "El id del cliente ".concat(id.toString().concat(" no existe")));
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(estudiante, HttpStatus.OK);
  }

  @GetMapping("/listarasistencia")
  public List<Asistencia> index() {
    return asistenciaservice.findAll();
  }

  @GetMapping("/asignaturas")
  public List<Asignatura> listarRegiones() {
    return asistenciaservice.findAllAsignaturas();
  }

  @GetMapping("/Modalidad")
  public List<Modalidad> listarModalidad() {
    return asistenciaservice.findAllModalidad();
  }

  @GetMapping("/Periodo")
  public List<Periodo> listarPeridod() {
    return asistenciaservice.findAllperio();
  }

  @GetMapping("/Periodos/{idempl}")
  public List<Periodo> listarPeridods(@PathVariable Long idempl) {
    return asistenciaservice.listarpaeriodo(idempl);
  }

  @GetMapping("/modalidades/{idempl}/{periodo}")
  public List<Modalidad> listarmodalidades(@PathVariable Long idempl,
      @PathVariable Long periodo) {
    return asistenciaservice.listarmodalidad(idempl, periodo);
  }

  @GetMapping("/cursos/{idempl}/{periodo}/{idmod}")
  public List<Curso> listarcurso(@PathVariable Long idempl, @PathVariable Long periodo,
      @PathVariable Long idmod) {
    return asistenciaservice.listarcurso(idempl, periodo, idmod);
  }

  @GetMapping("/paralelos/{idempl}/{periodo}/{modalidad}/{idcurso}")
  public List<Paralelo> listarparalelo(@PathVariable Long idempl, @PathVariable Long periodo,
      @PathVariable Long modalidad, @PathVariable Long idcurso) {
    return asistenciaservice.listarparalelo(idempl, periodo, modalidad, idcurso);
  }

  @GetMapping("/asignaturas/{empelado}/{idperiodo}/{modalidad}/{idcurso}/{idparalelo}")
  public List<Asignatura> listarasignatura(@PathVariable Long empelado,
      @PathVariable Long idperiodo, @PathVariable Long modalidad,
      @PathVariable Long idcurso, @PathVariable Long idparalelo) {
    return asistenciaservice.listarasignatura(empelado, idperiodo, modalidad, idcurso, idparalelo);
  }

  @GetMapping("/Curso")
  public List<Curso> listarCurso() {
    return asistenciaservice.findAllcurso();
  }

  @GetMapping("/Paralelo")
  public List<Paralelo> listarParalelo() {
    return asistenciaservice.findAllParalelo();
  }

  @PostMapping("/asistenciasave")
  public ResponseEntity<Asistencia> save(@RequestBody Asistencia asistencia) {
    Asistencia obj = asistenciaservice.save(asistencia);
    return new ResponseEntity<>(obj, HttpStatus.OK);
  }

  @GetMapping("/claseingresada")
  public Clase claseingresada() {
    return claseservice.ultimoingreso();
  }

  @PostMapping("/clasesave")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> createclase(@Valid @RequestBody Clase clase, BindingResult result) {
    Map<String, Object> response = new HashMap<>();

    if (result.hasErrors()) {
      List<String> errors = result.getFieldErrors().stream()
          .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
          .collect(Collectors.toList());
      response.put("errors", errors);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    Clase claseNew;
    try {
      claseNew = claseservice.save(clase);
    } catch (DataAccessException e) {
      response.put("mensaje", "Error al realizar el insert en la base de datos");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    response.put("mensaje", "La clase ha sido creada con éxito");
    response.put("clase", claseNew);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/listarclase")
  public List<Clase> findAll() {
    return claseservice.findAll();
  }

  @GetMapping("/asistenciadelete/{id}")
  public ResponseEntity<Asistencia> delete(@PathVariable Long id) {
    Asistencia asistencia = asistenciaservice.findById(id);
    if (asistencia != null) {
      asistenciaservice.delete(id);
      return new ResponseEntity<>(asistencia, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(asistencia, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/buscarestudiantesdeuncurso/{id}")
  public List<Persona> buscarcurso(@PathVariable Long id) {
    return asistenciaservice.buscarcurso(id);
  }

  @GetMapping("/filtrosdelaasistencia/{idmod}/{idper}/{idpar}/{idasig}/{idcurs}")
  public List<Estudiante> filtros(@PathVariable Long idmod, @PathVariable Long idper,
      @PathVariable Long idpar, @PathVariable Long idasig, @PathVariable Long idcurs) {
    return asistenciaservice.buscarcursomodalidad(idmod, idper, idpar, idasig, idcurs);
  }

  @GetMapping("/buscarestudianteporcedula/{ced}")
  public Estudiante fecha(@PathVariable String ced) {
    return asistenciaservice.buscarceduestudiante(ced);
  }

  @GetMapping("/buscarestudianteid/{id}")
  public List<Estudiante> estudianteid(@PathVariable Long id) {
    return asistenciaservice.mostrarinformacion(id);
  }

  @GetMapping("/buscaractualizar/{idMod}/{idPeriodo}/{idParalelo}/{idAsignatura}/{idCurso}/{fecha}/{docente}")
  public List<Asistencia> buscaractualizar(@PathVariable Long idMod, @PathVariable Long idPeriodo,
      @PathVariable Long idParalelo, @PathVariable Long idAsignatura, @PathVariable Long idCurso,
      @PathVariable String fecha, @PathVariable Long docente) throws ParseException {
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    Date auxfecha = formato.parse(fecha);
    return asistenciaservice.burcarasistencia(idMod, idPeriodo, idParalelo,
        idAsignatura, idCurso, auxfecha, docente);
  }

  @PutMapping("/updateasistencia/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> update(@Valid @RequestBody Asistencia usuario, BindingResult result,
      @PathVariable Long id) {
    Asistencia usuActual = asistenciaservice.findById(id);
    Map<String, Object> response = new HashMap<>();

    if (result.hasErrors()) {
      List<String> errors = result.getFieldErrors().stream()
          .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
          .collect(Collectors.toList());
      response.put("errors", errors);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    if (usuActual == null) {
      response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
          .concat(id.toString().concat(" no existe en la base de datos")));
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    try {
      usuActual.setEstadoAsis(usuario.getEstadoAsis());
      Asistencia usuUpdated = asistenciaservice.save(usuActual);
      response.put("mensaje", "El cliente ha sido actualizado con éxito");
      response.put("cliente", usuUpdated);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (DataAccessException e) {
      response.put("mensaje", "Error al actualizar la falta en la base de datos");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/claseactualizar/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Clase update(@RequestBody Clase clase, @PathVariable Long id) {
    Clase currentCliente = this.claseservice.findById(id);
    currentCliente.setFecClase(clase.getFecClase());
    currentCliente.setIdModalidad(clase.getIdModalidad());
    currentCliente.setIdCurso(clase.getIdCurso());
    currentCliente.setIdAsignatura(clase.getIdAsignatura());
    currentCliente.setIdPeriodo(clase.getIdPeriodo());
    currentCliente.setIdParalelo(clase.getIdParalelo());
    this.claseservice.save(currentCliente);
    return currentCliente;
  }

  @GetMapping("/mostrarfechasdefaltas/{ides}/{iddo}/{idasig}/{idcur}/{idpar}/{idmod}/{idperi}")
  public List<Clase> fechas(@PathVariable Long ides, @PathVariable Integer iddo,
      @PathVariable Integer idasig, @PathVariable Integer idcur, @PathVariable Integer idpar,
      @PathVariable Integer idmod, @PathVariable Integer idperi) {
    return claseservice.mostrarfechas(ides, iddo, idasig, idcur, idpar, idmod, idperi);
  }

  @GetMapping("/validarclase/{idMod}/{idPeriodo}/{idParalelo}/{idAsignatura}/{idCurso}/{fecha}/{docente}")
  public List<Asistencia> validarclase(@PathVariable Long idMod, @PathVariable Long idPeriodo,
      @PathVariable Long idParalelo, @PathVariable Long idAsignatura, @PathVariable Long idCurso,
      @PathVariable String fecha, @PathVariable Long docente) throws ParseException {
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    Date auxfecha = formato.parse(fecha);
    return asistenciaservice.burcarasistencia(idMod, idPeriodo, idParalelo,
        idAsignatura, idCurso, auxfecha, docente);
  }

  @GetMapping("/validarclass/{idDoc}/{idPeriodo}/{idMod}/{idCurso}/{idParalelo}/{idAsignatura}/{fechac}")
  public Long validarclass(@PathVariable Integer idDoc, @PathVariable Integer idPeriodo,
      @PathVariable Integer idMod, @PathVariable Integer idCurso, @PathVariable Integer idParalelo,
      @PathVariable Integer idAsignatura, @PathVariable String fechac) throws ParseException {
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    Date auxfecha = formato.parse(fechac);
    return claseservice.validarclass(idDoc, idPeriodo, idMod, idCurso, idParalelo, idAsignatura, auxfecha);
  }

  @GetMapping("/exportInvoice/{idEstudiante}/{idDocente}/{idAsignatura}/{usuario}/{fechaInicio}/{fechaFin}")
  public ResponseEntity<ByteArrayResource> exportinvoice(@PathVariable Long idEstudiante,
      @PathVariable Long idDocente, @PathVariable Long idAsignatura, @PathVariable Long usuario,
      @PathVariable Date fechaInicio, @PathVariable Date fechaFin) {
    return this.asistenciaservice.exportInvoice(idEstudiante, idDocente, idAsignatura,
        usuario, fechaInicio, fechaFin);
  }

  @GetMapping("/exportInvoicecurso/{idMod}/{idPeriodo}/{idParalelo}/{idAsignatura}/{idCurso}/{docente}/{usuario}/{fechaInicio}/{fechaFin}")
  public ResponseEntity<ByteArrayResource> exportinvoicepdfcurso(@PathVariable Long idMod,
      @PathVariable Long idPeriodo, @PathVariable Long idParalelo, @PathVariable Long idAsignatura,
      @PathVariable Long idCurso, @PathVariable Long docente, @PathVariable Long usuario,
      @PathVariable Date fechaInicio, @PathVariable Date fechaFin) {
    return this.asistenciaservice.exportInvoicepdfcursos(idMod, idPeriodo, idParalelo,
        idAsignatura, idCurso, docente, usuario, fechaInicio, fechaFin);
  }

}
