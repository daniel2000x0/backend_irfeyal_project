package com.irfeyal.asistencia.servicio;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

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
import com.irfeyal.parametrizacionacademica.dao.ParaleloRespository;
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
  private ParaleloRespository paraleloRepository;

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

      final File file = ResourceUtils.getFile("src/main/resources/PDF/reportesasistencias.jasper");
      final File imgLogo = ResourceUtils.getFile("src/main/resources/logo.png");
      final JasperReport report = (JasperReport) JRLoader.loadObject(file);

      numFaltas = asistenciasDocente.size();
      numFaltasAdmin = asistenciasAdmin.size();

      final Map<String, Object> parameters = new HashMap<>();
      parameters.put("id_estudiante", idEstudiante);
      parameters.put("tutor",
          empleadoDAO.findById(idDocente).get().getPersona().getNombre() + "  "
              + empleadoDAO.findById(idDocente).get().getPersona().getApellido());
      parameters.put("persoNom", estudiante.getIdPersona().getNombre());
      parameters.put("persoApe", estudiante.getIdPersona().getApellido());
      parameters.put("cedula", estudiante.getIdPersona().getCedula());

      List<String> cargos = rolUsuarioDAO.validacionadmin(usuario);
      boolean esAdmin = false;

      for (int i = 0; i < cargos.size(); i++) {
        if (cargos.get(i).equalsIgnoreCase("Administrador")) {
          esAdmin = true;
          parameters.put("numfalta", numFaltasAdmin);
          parameters.put("ds", new JRBeanCollectionDataSource(
              (Collection<?>) this.claseDao.mostrarfechasidpdfadmin(
                  idEstudiante, idAsignatura, fechaInicio, fechaFin)));
        } else {
          if (i == cargos.size() - 1 && !esAdmin) {
            parameters.put("numfalta", numFaltas);
            parameters.put("ds", new JRBeanCollectionDataSource(
                (Collection<?>) this.claseDao.mostrarfechasidpdf(
                    idEstudiante, idDocente, idAsignatura, fechaInicio, fechaFin)));
          }
        }
      }

      parameters.put("imgLogo", new FileInputStream(imgLogo));

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
      final File file = ResourceUtils.getFile("src/main/resources/PDF/reportecursosasistencias.jasper");
      final File imgLogo = ResourceUtils.getFile("src/main/resources/logo.png");
      final JasperReport report = (JasperReport) JRLoader.loadObject(file);

      final Map<String, Object> parameters = new HashMap<>();
      String periodoDescripcion = periodoRepository.getById(idPeriodo).getMalla().getDescripcion()
          + " " + periodoRepository.getById(idPeriodo).getAnoInicio()
          + "-" + periodoRepository.getById(idPeriodo).getAnoFin();

      parameters.put("desCurso", cursoRepository.getById(idCurso).getDescripcion());
      parameters.put("desParalelo", paraleloRepository.getById(idParalelo).getDescripcion());
      parameters.put("desPeriodo", periodoDescripcion);
      parameters.put("persoNom",
          empleadoDAO.findById(docente).get().getPersona().getNombre() + "  "
              + empleadoDAO.findById(docente).get().getPersona().getApellido());
      parameters.put("desModalidad", modalidadRepository.getById(idMod).getDescripcion());

      List<String> cargos = rolUsuarioDAO.validacionadmin(usuario);
      boolean esAdminCurso = false;

      for (int i = 0; i < cargos.size(); i++) {
        if (cargos.get(i).equalsIgnoreCase("Administrador")) {
          esAdminCurso = true;
          parameters.put("ds", new JRBeanCollectionDataSource(
              (Collection<?>) this.asistenciaDao.sistenciapdftutor(
                  idMod, idPeriodo, idParalelo, idCurso, fechaInicio, fechaFin)));
        } else {
          if (i == cargos.size() - 1 && !esAdminCurso) {
            parameters.put("ds", new JRBeanCollectionDataSource(
                (Collection<?>) this.asistenciaDao.sistenciapdf(
                    idMod, idPeriodo, idParalelo, idCurso, docente, fechaInicio, fechaFin)));
          }
        }
      }

      parameters.put("imgLogo", new FileInputStream(imgLogo));

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
