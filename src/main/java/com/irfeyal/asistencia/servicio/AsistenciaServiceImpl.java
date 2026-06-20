package com.irfeyal.asistencia.servicio;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.irfeyal.asistencia.dao.IAsistenciaDao;
import com.irfeyal.asistencia.dao.IClaseDao;
import com.irfeyal.asistencia.dto.AsistenciaDTO;
import com.irfeyal.asistencia.mapper.AsistenciaMapper;
import com.irfeyal.asistencia.interfaces.IAsistenciaService;
import com.irfeyal.asistencia.modelo.Asistencia;
import com.irfeyal.matricula.dao.IEstudianteDao;
import com.irfeyal.matricula.modelo.Estudiante;
import com.irfeyal.parametrizacionacademica.dao.AsignaturaRepository;
import com.irfeyal.parametrizacionacademica.dao.CursoRepository;
import com.irfeyal.parametrizacionacademica.dao.ModalidadRepository;
import com.irfeyal.parametrizacionacademica.dao.ParaleloRepository;
import com.irfeyal.parametrizacionacademica.dao.PeriodoRepository;
import com.irfeyal.parametrizacionacademica.modelo.Asignatura;
import com.irfeyal.parametrizacionacademica.modelo.Curso;
import com.irfeyal.parametrizacionacademica.modelo.Modalidad;
import com.irfeyal.parametrizacionacademica.modelo.Paralelo;
import com.irfeyal.parametrizacionacademica.modelo.Periodo;
import com.irfeyal.rolseguridad.dao.EmpleadoDAO;
import com.irfeyal.rolseguridad.dao.PersonaDAO;
import com.irfeyal.rolseguridad.dao.RolUsuarioDAO;
import com.irfeyal.rolseguridad.modelo.Persona;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Implementación del servicio de Asistencia.
 * Usa AsistenciaMapper para convertir entre entidad y DTO.
 * Los reportes PDF usan JasperReports directamente con entidades
 * (no DTOs) porque requieren acceso a relaciones completas.
 */
@Service
public class AsistenciaServiceImpl implements IAsistenciaService {

  @Autowired
  private EmpleadoDAO empleadoDAO;

  @Autowired
  private RolUsuarioDAO rolUsuarioDAO;

  @Autowired
  private IAsistenciaDao asistenciaDao;

  @Autowired
  private PersonaDAO personaDao;

  @Autowired
  private IEstudianteDao estudianteDao;

  @Autowired
  private IClaseDao claseDao;

  @Autowired
  private ParaleloRepository paraleloRepository;

  @Autowired
  private PeriodoRepository periodoRepository;

  @Autowired
  private ModalidadRepository modalidadRepository;

  @Autowired
  private AsignaturaRepository asignaturaRepository;

  @Autowired
  private CursoRepository cursoRepository;

  @Autowired
  private AsistenciaMapper asistenciaMapper;

  @Override
  public List<AsistenciaDTO> findAll() {
    return asistenciaDao.findAll().stream()
        .map(asistenciaMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Page<AsistenciaDTO> findAll(Pageable pageable) {
    return asistenciaDao.findAll(pageable)
        .map(asistenciaMapper::toDto);
  }

  @Override
  public AsistenciaDTO findById(Long id) {
    return asistenciaDao.findById(id)
        .map(asistenciaMapper::toDto)
        .orElse(null);
  }

  @Override
  public AsistenciaDTO save(AsistenciaDTO asistenciaDTO) {
    Asistencia entity = asistenciaMapper.toEntity(asistenciaDTO);
    Asistencia saved = asistenciaDao.save(entity);
    return asistenciaMapper.toDto(saved);
  }

  @Override
  public void delete(Long id) {
    asistenciaDao.deleteById(id);
  }

  @Override
  public List<Persona> buscarCurso(Long id) {
    return personaDao.buscarcurso(id);
  }

  @Override
  public List<Estudiante> buscarCursoModalidad(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso) {
    return estudianteDao.buscarcursotodofil(idMod, idPeriodo, idParalelo, idAsignatura, idCurso);
  }

  @Override
  public Estudiante buscarCedulaEstudiante(String cedula) {
    return estudianteDao.buscarcedulaestudiante(cedula);
  }

  @Override
  public List<Asignatura> findAllAsignaturas() {
    return asignaturaRepository.findAll();
  }

  @Override
  public List<Paralelo> findAllParalelo() {
    return paraleloRepository.findAll();
  }

  @Override
  public List<Modalidad> findAllModalidad() {
    return modalidadRepository.findAll();
  }

  @Override
  public List<Periodo> findAllPeriodo() {
    return periodoRepository.findAll();
  }

  @Override
  public List<Curso> findAllCurso() {
    return cursoRepository.findAll();
  }

  @Override
  public List<AsistenciaDTO> buscarAsistencia(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha, Long docente) {
    return asistenciaDao.actualizarfiltros(idMod, idPeriodo, idParalelo,
        idAsignatura, idCurso, fecha, docente).stream()
        .map(asistenciaMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<Estudiante> mostrarInformacion(long id) {
    return estudianteDao.findestudianteid(id);
  }

  @Override
  public List<Periodo> listarPeriodo(Long empleado) {
    return periodoRepository.listarperiodoasistencia(empleado);
  }

  @Override
  public List<Modalidad> listarModalidad(Long empleado, Long idPeriodo) {
    return modalidadRepository.listarmodalidadasistencia(empleado, idPeriodo);
  }

  @Override
  public List<Curso> listarCurso(Long empleado, Long periodo, Long idModalidad) {
    return cursoRepository.listarcursoasistencia(empleado, periodo, idModalidad);
  }

  @Override
  public List<Paralelo> listarParalelo(Long empleado, Long periodo, Long modalidad, Long idCurso) {
    return paraleloRepository.listarparaleloasistencia(empleado, periodo, modalidad, idCurso);
  }

  @Override
  public List<Asignatura> listarAsignatura(Long empleado, Long idPeriodo, Long modalidad,
      Long idCurso, Long idParalelo) {
    return asignaturaRepository.listarasignaturaasistencia(empleado, idPeriodo, modalidad, idCurso, idParalelo);
  }

  @Override
  public ResponseEntity<ByteArrayResource> exportInvoice(Long idEstudiante, Long idDocente,
      Long idAsignatura, Long usuario, Date fechaInicio, Date fechaFin) {
    try {
      Integer numFaltas = 0;
      Integer numFaltasAdmin = 0;

      List<Asistencia> asistenciasDocente = this.asistenciaDao.obtenerIdEstudiante(
          idEstudiante, idDocente, idAsignatura, fechaInicio, fechaFin);
      List<Asistencia> asistenciasAdmin = this.asistenciaDao.obtenerIdEstudianteAdmin(
          idEstudiante, idAsignatura, fechaInicio, fechaFin);
      Estudiante estudiante = this.estudianteDao.findestudianteidpdf(idEstudiante);

      InputStream reportStream = new ClassPathResource("PDF/reportesasistencias.jasper").getInputStream();
      InputStream logoStream = new ClassPathResource("logo.png").getInputStream();
      final JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

      numFaltas = asistenciasDocente.size();
      numFaltasAdmin = asistenciasAdmin.size();

      var empleadoOpt = empleadoDAO.findById(idDocente);
      if (empleadoOpt.isEmpty() || estudiante == null || estudiante.getIdPersona() == null) {
        throw new RuntimeException("Empleado o estudiante no encontrado");
      }
      var empleado = empleadoOpt.get();

      final Map<String, Object> parameters = new HashMap<>();
      parameters.put("id_estudiante", idEstudiante);
      parameters.put("tutor",
          empleado.getPersona().getNombre() + "  "
              + empleado.getPersona().getApellido());
      parameters.put("persoNom", estudiante.getIdPersona().getNombre());
      parameters.put("persoApe", estudiante.getIdPersona().getApellido());
      parameters.put("cedula", estudiante.getIdPersona().getCedula());

      List<String> cargos = rolUsuarioDAO.validacionadmin(usuario);
      boolean esAdmin = false;

      parameters.put("numfalta", numFaltas);
      parameters.put("ds", new JRBeanCollectionDataSource(
          (Collection<?>) this.claseDao.mostrarfechasidpdf(
              idEstudiante, idDocente, idAsignatura, fechaInicio, fechaFin)));

      for (int i = 0; i < cargos.size(); i++) {
        if (cargos.get(i).equalsIgnoreCase("Administrador")) {
          esAdmin = true;
          parameters.put("numfalta", numFaltasAdmin);
          parameters.put("ds", new JRBeanCollectionDataSource(
              (Collection<?>) this.claseDao.mostrarfechasidpdfadmin(
                  idEstudiante, idAsignatura, fechaInicio, fechaFin)));
          break;
        }
      }

      parameters.put("imgLogo", logoStream);

      JasperPrint jPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
      byte[] reporte = JasperExportManager.exportReportToPdf(jPrint);

      String fechaFormateada = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
      String fileName = "InvoicePDF:" + idEstudiante + "generateDate:" + fechaFormateada + ".pdf";

      ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
          .filename(fileName).build();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentDisposition(contentDisposition);

      return ResponseEntity.ok()
          .contentLength(reporte.length)
          .contentType(MediaType.APPLICATION_PDF)
          .headers(headers)
          .body(new ByteArrayResource(reporte));

    } catch (Exception e) {
      throw new RuntimeException("Error al generar reporte PDF de asistencia", e);
    }
  }

  @Override
  public ResponseEntity<ByteArrayResource> exportInvoiceCurso(Long idMod, Long idPeriodo,
      Long idParalelo, Long idAsignatura, Long idCurso, Long docente, Long usuario,
      Date fechaInicio, Date fechaFin) {
    try {
      InputStream reportStream = new ClassPathResource("PDF/reportecursosasistencias.jasper").getInputStream();
      InputStream logoStream = new ClassPathResource("logo.png").getInputStream();
      final JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

      var periodo = periodoRepository.findById(idPeriodo)
          .orElseThrow(() -> new RuntimeException("Periodo no encontrado: " + idPeriodo));
      var curso = cursoRepository.findById(idCurso)
          .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + idCurso));
      var paralelo = paraleloRepository.findById(idParalelo)
          .orElseThrow(() -> new RuntimeException("Paralelo no encontrado: " + idParalelo));
      var empleadoOpt = empleadoDAO.findById(docente);
      if (empleadoOpt.isEmpty()) {
        throw new RuntimeException("Empleado no encontrado: " + docente);
      }
      var empleado = empleadoOpt.get();
      var modalidad = modalidadRepository.findById(idMod)
          .orElseThrow(() -> new RuntimeException("Modalidad no encontrada: " + idMod));

      String periodoDescripcion = periodo.getMalla().getDescripcion()
          + " " + periodo.getAnoInicio()
          + "-" + periodo.getAnoFin();

      final Map<String, Object> parameters = new HashMap<>();
      parameters.put("desCurso", curso.getDescripcion());
      parameters.put("desParalelo", paralelo.getDescripcion());
      parameters.put("desPeriodo", periodoDescripcion);
      parameters.put("persoNom",
          empleado.getPersona().getNombre() + "  "
              + empleado.getPersona().getApellido());
      parameters.put("desModalidad", modalidad.getDescripcion());

      List<String> cargos = rolUsuarioDAO.validacionadmin(usuario);
      boolean esAdminCurso = false;

      parameters.put("ds", new JRBeanCollectionDataSource(
          (Collection<?>) this.asistenciaDao.sistenciapdf(
              idMod, idPeriodo, idParalelo, idCurso, docente, fechaInicio, fechaFin)));

      for (int i = 0; i < cargos.size(); i++) {
        if (cargos.get(i).equalsIgnoreCase("Administrador")) {
          esAdminCurso = true;
          parameters.put("ds", new JRBeanCollectionDataSource(
              (Collection<?>) this.asistenciaDao.sistenciapdftutor(
                  idMod, idPeriodo, idParalelo, idCurso, fechaInicio, fechaFin)));
          break;
        }
      }

      parameters.put("imgLogo", logoStream);

      JasperPrint jPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
      byte[] reporte = JasperExportManager.exportReportToPdf(jPrint);

      String fechaFormateada = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
      String fileName = "InvoicePDF:" + idAsignatura + "generateDate:" + fechaFormateada + ".pdf";

      ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
          .filename(fileName).build();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentDisposition(contentDisposition);

      return ResponseEntity.ok()
          .contentLength(reporte.length)
          .contentType(MediaType.APPLICATION_PDF)
          .headers(headers)
          .body(new ByteArrayResource(reporte));

    } catch (Exception e) {
      throw new RuntimeException("Error al generar reporte PDF de asistencia por curso", e);
    }
  }

}
