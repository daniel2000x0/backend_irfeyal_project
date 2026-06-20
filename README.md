# IRFEYAL - Módulo de Asistencia

Sistema de gestión de asistencias para instituciones educativas. Desarrollado como parte del backend del sistema IRFEYAL, una plataforma integral para la administración académica.

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Tecnologías](#tecnologías)
- [Arquitectura del Módulo](#arquitectura-del-módulo)
- [API REST - Endpoints](#api-rest---endpoints)
- [Fragmentos Destacados](#fragmentos-destacados)
- [Diagrama de Entidades](#diagrama-de-entidades)
- [Características Técnicas](#características-técnicas)
- [Configuración y Ejecución](#configuración-y-ejecución)

---

## Descripción General

El **módulo de asistencia** permite a docentes y administradores registrar, consultar y reportar la asistencia de estudiantes en un sistema educativo multi-sede. Las funcionalidades principales incluyen:

- **Registro de clases**: Los docentes registran las clases impartidas con fecha, materia, curso, paralelo y modalidad.
- **Toma de asistencia**: Registro individual de asistencia (presente/ausente) por estudiante y por clase.
- **Filtros avanzados**: Búsqueda de asistencias por modalidad, período, paralelo, asignatura, curso, fecha y docente.
- **Roles y permisos**: Los administradores ven todas las asistencias; los docentes solo las de sus cursos.
- **Reportes PDF**: Generación de reportes de asistencia individual por estudiante y reportes por curso usando JasperReports.
- **Validación de clases**: Evita el registro duplicado de una misma clase.

---

## Tecnologías

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 11 | Lenguaje base |
| Spring Boot | 2.6.4 | Framework principal |
| Spring Data JPA | - | Persistencia y repositorios |
| Spring Web | - | API REST |
| Spring Validation | - | Validación de datos |
| PostgreSQL | - | Base de datos relacional |
| JasperReports | 6.18.1 | Generación de reportes PDF |
| Maven | - | Gestión de dependencias |

---

## Arquitectura del Módulo

El módulo sigue una arquitectura en capas dentro del patrón MVC de Spring:

```
com.irfeyal.asistencia/
├── modelo/           # Entidades JPA (Asistencia, Clase)
├── dao/              # Repositorios Spring Data JPA
├── interfaces/       # Interfaces de servicio
├── servicio/         # Implementaciones de lógica de negocio
└── controlador/      # Controladores REST
```

### Dependencias con otros módulos

El módulo se integra con:

| Módulo | Entidades usadas | Propósito |
|---|---|---|
| `matricula` | `Estudiante` | Identificar estudiantes |
| `parametrizacionacademica` | `Asignatura`, `Curso`, `Modalidad`, `Paralelo`, `Periodo` | Contexto académico |
| `rolseguridad` | `Empleado`, `Persona`, `Usuario`, `Rol`, `RolUsuario` | Autenticación y roles |

---

## API REST - Endpoints

### Asistencias

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/asistencia/listarasistencia` | Lista todas las asistencias |
| `POST` | `/asistencia/asistenciasave` | Guarda una nueva asistencia |
| `PUT` | `/asistencia/updateasistencia/{id}` | Actualiza estado de asistencia |
| `GET` | `/asistencia/asistenciadelete/{id}` | Elimina una asistencia |
| `GET` | `/asistencia/buscaractualizar/{idMod}/{idPeriodo}/{idParalelo}/{idAsignatura}/{idCurso}/{fecha}/{docente}` | Busca asistencias con filtros |

### Clases

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/asistencia/listarclase` | Lista todas las clases |
| `POST` | `/asistencia/clasesave` | Crea una nueva clase |
| `PUT` | `/asistencia/claseactualizar/{id}` | Actualiza una clase |
| `GET` | `/asistencia/claseingresada` | Obtiene la última clase registrada |
| `GET` | `/asistencia/validarclass/{idDoc}/{idPeriodo}/{idMod}/{idCurso}/{idParalelo}/{idAsignatura}/{fechac}` | Valida si una clase ya existe |

### Catálogos (con filtro por docente)

| Método | Endpoint |
|---|---|
| `GET` | `/asistencia/Periodos/{idempl}` |
| `GET` | `/asistencia/modalidades/{idempl}/{periodo}` |
| `GET` | `/asistencia/cursos/{idempl}/{periodo}/{idmod}` |
| `GET` | `/asistencia/paralelos/{idempl}/{periodo}/{modalidad}/{idcurso}` |
| `GET` | `/asistencia/asignaturas/{empelado}/{idperiodo}/{modalidad}/{idcurso}/{idparalelo}` |
| `GET` | `/asistencia/filtrosdelaasistencia/{idmod}/{idper}/{idpar}/{idasig}/{idcurs}` |

### Estudiantes

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/asistencia/asist/{id}` | Obtiene estudiante por ID |
| `GET` | `/asistencia/buscarestudianteporcedula/{ced}` | Busca estudiante por cédula |
| `GET` | `/asistencia/buscarestudianteid/{id}` | Información detallada del estudiante |

### Reportes PDF

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/asistencia/exportInvoice/{idEstudiante}/{idDocente}/{idAsignatura}/{usuario}/{fechaInicio}/{fechaFin}` | Reporte individual por estudiante |
| `GET` | `/asistencia/exportInvoicecurso/{idMod}/{idPeriodo}/{idParalelo}/{idAsignatura}/{idCurso}/{docente}/{usuario}/{fechaInicio}/{fechaFin}` | Reporte por curso |

---

## Fragmentos Destacados

### 1. Entidad Asistencia (JPA)

```java
@Entity
@Table(name = "asistencia")
public class Asistencia implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_asistencia")
  private Long idAsistencia;

  @Column(name = "estado_asis")
  private Boolean estadoAsis;  // true = presente, false = ausente

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
  private Clase idClase;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_estudiante", referencedColumnName = "id_estudiante")
  private Estudiante idEstudiante;

  // Getters computados para el reporte Jasper
  public String getNombre() {
    return this.idEstudiante.getIdPersona().getNombre();
  }

  public String getApellido() {
    return this.idEstudiante.getIdPersona().getApellido();
  }

  public String getCedula() {
    return this.idEstudiante.getIdPersona().getCedula();
  }
}
```

### 2. Consultas nativas con Spring Data JPA

```java
public interface IAsistenciaDao extends JpaRepository<Asistencia, Long> {

  @Query(value = "SELECT * FROM Asistencia asis "
      + "JOIN clase cls ON cls.id_clase = asis.id_clase "
      + "WHERE cls.id_modalidad_id_modalidad=?1 "
      + "AND cls.id_periodo_id_periodo=?2 "
      + "AND cls.id_paralelo=?3 "
      + "AND cls.id_asignatura=?4 "
      + "AND cls.id_curso=?5 "
      + "AND cls.fec_clase=?6",
      nativeQuery = true)
  List<Asistencia> buscarasistencia(Long idMod, Long idPeriodo, Long idParalelo,
      Long idAsignatura, Long idCurso, Date fecha);
}
```

### 3. Lógica de generación de reportes PDF con JasperReports

```java
@Override
public ResponseEntity<ByteArrayResource> exportInvoice(Long idEstudiante, Long idDocente,
    Long idAsignatura, Long usuario, Date fechaInicio, Date fechaFin) {

  // Cargar el reporte Jasper compilado
  final File file = ResourceUtils.getFile("src/main/resources/PDF/reportesasistencias.jasper");
  final JasperReport report = (JasperReport) JRLoader.loadObject(file);

  // Parámetros del reporte
  final Map<String, Object> parameters = new HashMap<>();
  parameters.put("persoNom", estudiante.getIdPersona().getNombre());
  parameters.put("persoApe", estudiante.getIdPersona().getApellido());
  parameters.put("cedula", estudiante.getIdPersona().getCedula());

  // Data source: lista de asistencias filtradas
  parameters.put("ds", new JRBeanCollectionDataSource(listaAsistencias));

  // Renderizar PDF
  JasperPrint jPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
  byte[] reporte = JasperExportManager.exportReportToPdf(jPrint);

  return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_PDF)
      .body(new ByteArrayResource(reporte));
}
```

### 4. Controlador REST con validación y manejo de errores

```java
@PostMapping("/clasesave")
@ResponseStatus(HttpStatus.CREATED)
public ResponseEntity<?> createclase(@Valid @RequestBody Clase clase, BindingResult result) {

  if (result.hasErrors()) {
    List<String> errors = result.getFieldErrors().stream()
        .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
        .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(Map.of("errors", errors));
  }

  try {
    Clase claseNew = claseservice.save(clase);
    return new ResponseEntity<>(
        Map.of("mensaje", "Clase creada con éxito", "clase", claseNew),
        HttpStatus.CREATED);
  } catch (DataAccessException e) {
    return new ResponseEntity<>(
        Map.of("mensaje", "Error en base de datos", "error", e.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
```

### 5. Validación de roles para reportes (docente vs administrador)

```java
List<String> cargos = rolUsuarioDAO.validacionadmin(usuario);
boolean esAdmin = false;

for (int i = 0; i < cargos.size(); i++) {
  if (cargos.get(i).equalsIgnoreCase("Administrador")) {
    esAdmin = true;
    // Administrador: ve todas las asistencias
    parameters.put("ds", new JRBeanCollectionDataSource(
        clasedao.mostrarfechasidpdfadmin(idEstudiante, idAsignatura, fechaInicio, fechaFin)));
    break;
  }
}

if (!esAdmin) {
  // Docente: solo ve asistencias de sus cursos
  parameters.put("ds", new JRBeanCollectionDataSource(
      clasedao.mostrarfechasidpdf(idEstudiante, idDocente, idAsignatura, fechaInicio, fechaFin)));
}
```

---

## Diagrama de Entidades

```
┌─────────────┐     ┌──────────────────┐     ┌──────────────┐
│   Clase     │────▶│   Asistencia     │◀────│  Estudiante  │
├─────────────┤     ├──────────────────┤     ├──────────────┤
│ id_clase PK │     │ id_asistencia PK │     │ id_est PK    │
│ fec_clase   │     │ estado_asis      │     │ id_persona FK│
│ id_curso FK │     │ id_clase FK      │     └──────┬───────┘
│ id_paralelo │     │ id_estudiante FK │            │
│ id_asignat. │     └──────────────────┘     ┌──────┴───────┐
│ id_docente  │     ┌──────────────────┐     │   Persona    │
│ id_modalidad│     │    Empleado      │     ├──────────────┤
│ id_periodo  │     ├──────────────────┤     │ nombre       │
└─────────────┘     │ id_empleado PK   │     │ apellido     │
       │            │ id_persona FK    │     │ cedula       │
       │            └────────┬─────────┘     └──────────────┘
       │                     │
       ▼                     ▼
┌───────────────┐  ┌──────────────────┐
│   Curso       │  │     Usuario      │
├───────────────┤  ├──────────────────┤
│ Asignatura    │  │ RolUsuario ── Rol│
│ Paralelo      │  └──────────────────┘
│ Modalidad     │
│ Periodo       │
└───────────────┘
```

---

## Características Técnicas

### Buenas prácticas aplicadas

| Práctica | Implementación |
|---|---|
| **Arquitectura en capas** | Separación clara en modelo/dao/interfaces/servicio/controlador |
| **Manejo de errores** | Try-catch con `DataAccessException` y `ResponseEntity` con mensajes descriptivos |
| **Validación** | Uso de `@Valid` + `BindingResult` para validar datos de entrada |
| **Lazy loading** | `FetchType.LAZY` en relaciones JPA para optimizar consultas |
| **Consultas nativas optimizadas** | JPQL nativo con JOINS para consultas complejas multi-tabla |
| **Control de acceso por roles** | Validación de roles (Administrador vs Docente) en generación de reportes |
| **DTOs computados** | Getters que exponen datos de entidades relacionadas (nombre, apellido, cédula) |

### Mejoras realizadas sobre el código original

- ✅ Eliminación de imports duplicados y no utilizados
- ✅ Corrección de nombres de campo en `@JsonIgnoreProperties` (de `id_clase` a `idClase`)
- ✅ Eliminación de getters/setters duplicados en entidad `Clase`
- ✅ Reemplazo de `com.sun.istack.NotNull` por validación Spring estándar
- ✅ Corrección de dependencia directa a implementación `EstudianteServiceImpl` → interfaz `IEstudianteService`
- ✅ Eliminación de variables de instancia no utilizadas
- ✅ Nomenclatura de variables consistente (camelCase)

---

## Configuración y Ejecución

### Prerrequisitos

- Java 11+
- Maven 3.6+
- PostgreSQL 12+

### Base de datos

```sql
CREATE DATABASE irfeyal_db;
```

### Configuración

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/irfeyal_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Ejecutar

```bash
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080/api/v1/asistencia/`

---

## Licencia

Este proyecto es parte del portafolio del desarrollador. El código completo del sistema IRFEYAL es privado y pertenece a sus respectivos dueños. Este repositorio contiene únicamente el módulo de asistencia con fines demostrativos.
