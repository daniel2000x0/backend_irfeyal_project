package com.irfeyal.asistencia.servicio;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.irfeyal.asistencia.dao.IAsistenciaDao;
import com.irfeyal.asistencia.dao.IClaseDao;
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

@Service
public class AsistenciaServiceImpl implements IAsistenciaService {

  @Autowired
  private EmpleadoDAO empleadoDAO;

  @Autowired
  private RolUsuarioDAO rolUsuarioDAO;

  @Autowired
  private IAsistenciaDao asistenciadao;

  @Autowired
  private PersonaDAO personadao;

  @Autowired
  private IEstudianteDao estudiantedao;

  @Autowired
  private IClaseDao clasedao;

  @Autowired
  private ParaleloRespository paralelo;

  @Autowired
  private PeriodoRepository periodo;

  @Autowired
  private ModalidadRepository modalidad;

  @Autowired
  private AsignaturaRepository asignatura;

  @Autowired
  private CursoRepository curso;

  @Override
  public List<Asistencia> findAll() {
    return asistenciadao.findAll();
  }

  @Override
  public Asistencia findById(Long id) {
    return asistenciadao.findById(id).orElse(null);
  }

  @Override
  public Asistencia save(Asistencia asistencia) {
    return asistenciadao.save(asistencia);
  }

  @Override
  public void delete(Long id) {
    asistenciadao.deleteById(id);
  }

  @Override
  public List<Persona> buscarcurso(Long id) {
    return personadao.buscarcurso(id);
  }

  @Override
  public List<Estudiante> buscarcursomodalidad(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso) {
    return estudiantedao.buscarcursotodofil(idMod, idPeriodo, idParalelo, idAsignatura, idCurso);
  }

  @Override
  public Estudiante buscarceduestudiante(String cedula) {
    return estudiantedao.buscarcedulaestudiante(cedula);
  }

  @Override
  public List<Modalidad> modalidaddocente(Integer idDocente) {
    return null;
  }

  @Override
  public List<Asignatura> findAllAsignaturas() {
    return asignatura.findAll();
  }

  @Override
  public List<Paralelo> findAllParalelo() {
    return paralelo.findAll();
  }

  @Override
  public List<Modalidad> findAllModalidad() {
    return modalidad.findAll();
  }

  @Override
  public List<Periodo> findAllperio() {
    return periodo.findAll();
  }

  @Override
  public List<Curso> findAllcurso() {
    return curso.findAll();
  }

  @Override
  public List<Asistencia> burcarasistencia(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha, Long docente) {
    return asistenciadao.actualizarfiltros(idMod, idPeriodo, idParalelo,
        idAsignatura, idCurso, fecha, docente);
  }

  @Override
  public List<Estudiante> mostrarinformacion(long id) {
    return estudiantedao.findestudianteid(id);
  }

  @Override
  public List<Periodo> listarpaeriodo(Long empleado) {
    return periodo.listarperiodoasistencia(empleado);
  }

  @Override
  public List<Modalidad> listarmodalidad(Long empleado, Long idPeriodo) {
    return modalidad.listarmodalidadasistencia(empleado, idPeriodo);
  }

  @Override
  public List<Curso> listarcurso(Long empleado, Long periodo, Long idModalidad) {
    return curso.listarcursoasistencia(empleado, periodo, idModalidad);
  }

  @Override
  public List<Paralelo> listarparalelo(Long empleado, Long periodo, Long modalidad, Long idCurso) {
    return paralelo.listarparaleloasistencia(empleado, periodo, modalidad, idCurso);
  }

  @Override
  public List<Asignatura> listarasignatura(Long empleado, Long idPeriodo, Long modalidad,
      Long idCurso, Long idParalelo) {
    return asignatura.listarasignaturaasistencia(empleado, idPeriodo, modalidad, idCurso, idParalelo);
  }

  @Override
  public List<Asistencia> actualizarfiltros(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha, Long docente) {
    return asistenciadao.actualizarfiltros(idMod, idPeriodo, idParalelo,
        idAsignatura, idCurso, fecha, docente);
  }

  @Override
  public ResponseEntity<ByteArrayResource> exportInvoice(Long idEstudiante, Long idDocente,
      Long idAsignatura, Long usuario, Date fechaInicio, Date fechaFin) {
    try {
      Integer numfalta = 0;
      Integer numfaltaAdmin = 0;

      List<Asistencia> asispedf = this.asistenciadao.obtenerIdEstudiante(
          idEstudiante, idDocente, idAsignatura, fechaInicio, fechaFin);
      List<Asistencia> asispedfAdmin = this.asistenciadao.obtenerIdEstudianteAdmin(
          idEstudiante, idAsignatura, fechaInicio, fechaFin);
      Estudiante estudiantedaoa = this.estudiantedao.findestudianteidpdf(idEstudiante);

      final File file = ResourceUtils.getFile("src/main/resources/PDF/reportesasistencias.jasper");
      final File imgLogo = ResourceUtils.getFile("src/main/resources/logo.png");
      final JasperReport report = (JasperReport) JRLoader.loadObject(file);

      numfalta = asispedf.size();
      numfaltaAdmin = asispedfAdmin.size();

      final Map<String, Object> parameters = new HashMap<>();
      parameters.put("id_estudiante", idEstudiante);
      parameters.put("tutor",
          empleadoDAO.findById(idDocente).get().getPersona().getNombre() + "  "
              + empleadoDAO.findById(idDocente).get().getPersona().getApellido());
      parameters.put("persoNom", estudiantedaoa.getIdPersona().getNombre());
      parameters.put("persoApe", estudiantedaoa.getIdPersona().getApellido());
      parameters.put("cedula", estudiantedaoa.getIdPersona().getCedula());

      List<String> cargos = rolUsuarioDAO.validacionadmin(usuario);
      boolean validadmin = false;

      for (int i = 0; i < cargos.size(); i++) {
        if (cargos.get(i).equalsIgnoreCase("Administrador")) {
          validadmin = true;
          parameters.put("numfalta", numfaltaAdmin);
          parameters.put("ds", new JRBeanCollectionDataSource(
              (Collection<?>) this.clasedao.mostrarfechasidpdfadmin(
                  idEstudiante, idAsignatura, fechaInicio, fechaFin)));
        } else {
          if (i == cargos.size() - 1 && !validadmin) {
            parameters.put("numfalta", numfalta);
            parameters.put("ds", new JRBeanCollectionDataSource(
                (Collection<?>) this.clasedao.mostrarfechasidpdf(
                    idEstudiante, idDocente, idAsignatura, fechaInicio, fechaFin)));
          }
        }
      }

      parameters.put("imgLogo", new FileInputStream(imgLogo));

      JasperPrint jPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
      byte[] reporte = JasperExportManager.exportReportToPdf(jPrint);

      String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
      String filename = "InvoicePDF:" + idEstudiante + "generateDate:" + sdf + ".pdf";

      ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
          .filename(filename).build();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentDisposition(contentDisposition);

      return ResponseEntity.ok()
          .contentLength(reporte.length)
          .contentType(MediaType.APPLICATION_PDF)
          .headers(headers)
          .body(new ByteArrayResource(reporte));

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public ResponseEntity<ByteArrayResource> exportInvoicepdfcursos(Long idMod, Long idPeriodo,
      Long idParalelo, Long idAsignatura, Long idCurso, Long docente, Long usuario,
      Date fechaInicio, Date fechaFin) {
    try {
      final File file = ResourceUtils.getFile("src/main/resources/PDF/reportecursosasistencias.jasper");
      final File imgLogo = ResourceUtils.getFile("src/main/resources/logo.png");
      final JasperReport report = (JasperReport) JRLoader.loadObject(file);

      final Map<String, Object> parameters = new HashMap<>();
      String periodocripcion = periodo.getById(idPeriodo).getMalla().getDescripcion()
          + " " + periodo.getById(idPeriodo).getAnoInicio()
          + "-" + periodo.getById(idPeriodo).getAnoFin();

      parameters.put("desCurso", curso.getById(idCurso).getDescripcion());
      parameters.put("desParalelo", paralelo.getById(idParalelo).getDescripcion());
      parameters.put("desPeriodo", periodocripcion);
      parameters.put("persoNom",
          empleadoDAO.findById(docente).get().getPersona().getNombre() + "  "
              + empleadoDAO.findById(docente).get().getPersona().getApellido());
      parameters.put("desModalidad", modalidad.getById(idMod).getDescripcion());

      List<String> cargos = rolUsuarioDAO.validacionadmin(usuario);
      boolean validadmincurso = false;

      for (int i = 0; i < cargos.size(); i++) {
        if (cargos.get(i).equalsIgnoreCase("Administrador")) {
          validadmincurso = true;
          parameters.put("ds", new JRBeanCollectionDataSource(
              (Collection<?>) this.asistenciadao.sistenciapdftutor(
                  idMod, idPeriodo, idParalelo, idCurso, fechaInicio, fechaFin)));
        } else {
          if (i == cargos.size() - 1 && !validadmincurso) {
            parameters.put("ds", new JRBeanCollectionDataSource(
                (Collection<?>) this.asistenciadao.sistenciapdf(
                    idMod, idPeriodo, idParalelo, idCurso, docente, fechaInicio, fechaFin)));
          }
        }
      }

      parameters.put("imgLogo", new FileInputStream(imgLogo));

      JasperPrint jPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
      byte[] reporte = JasperExportManager.exportReportToPdf(jPrint);

      String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
      String filename = "InvoicePDF:" + idAsignatura + "generateDate:" + sdf + ".pdf";

      ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
          .filename(filename).build();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentDisposition(contentDisposition);

      return ResponseEntity.ok()
          .contentLength(reporte.length)
          .contentType(MediaType.APPLICATION_PDF)
          .headers(headers)
          .body(new ByteArrayResource(reporte));

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
