# IRFEYAL - Módulo de Asistencia

Sistema de gestión de asistencias para instituciones educativas. Desarrollado como parte del backend del sistema IRFEYAL, una plataforma integral para la administración académica.

## Contenido

- [Descripción General](#descripción-general)
- [Tecnologías](#tecnologías)
- [Arquitectura del Módulo](#arquitectura-del-módulo)
- [API REST - Endpoints](#api-rest---endpoints)
- [Autenticación JWT](#autenticación-jwt)
- [Swagger / OpenAPI](#swagger--openapi)
- [Fragmentos Destacados](#fragmentos-destacados)
- [Diagrama de Entidades](#diagrama-de-entidades)
- [Características Técnicas](#características-técnicas)
- [Mejoras y Correcciones](#mejoras-y-correcciones)
- [Configuración y Ejecución](#configuración-y-ejecución)
- [Docker](#docker)

---

## Descripción General

El **módulo de asistencia** permite a docentes y administradores registrar, consultar y reportar la asistencia de estudiantes en un sistema educativo multi-sede. Las funcionalidades principales incluyen:

- **Autenticación JWT**: Login seguro con tokens Bearer y roles (Administrador/Docente).
- **Registro de clases**: Los docentes registran las clases impartidas con fecha, materia, curso, paralelo y modalidad.
- **Toma de asistencia**: Registro individual de asistencia (presente/ausente) por estudiante y por clase.
- **Filtros avanzados**: Búsqueda de asistencias por modalidad, período, paralelo, asignatura, curso, fecha y docente.
- **Roles y permisos**: Los administradores ven todas las asistencias; los docentes solo las de sus cursos.
- **Reportes PDF**: Generación de reportes de asistencia individual por estudiante y reportes por curso usando JasperReports.
- **Validación de clases**: Evita el registro duplicado de una misma clase.
- **Paginación**: Endpoints de listado con soporte `Pageable` de Spring Data.
- **Documentación Swagger UI**: Interfaz interactiva para explorar y probar la API.

---

## Tecnologías

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 11 | Lenguaje base |
| Spring Boot | 2.6.4 | Framework principal |
| Spring Data JPA | - | Persistencia y repositorios |
| Spring Security | - | Autenticación y autorización JWT |
| Spring Validation | - | Validación de datos de entrada |
| SpringDoc OpenAPI | 1.6.15 | Documentación Swagger UI |
| PostgreSQL | - | Base de datos relacional |
| JasperReports | 6.18.1 | Generación de reportes PDF |
| JJWT | 0.11.5 | Tokens JWT |
| H2 (test) | - | Base de datos embebida para tests |
| Maven | - | Gestión de dependencias |
| Docker | - | Contenedorización |

---

## Arquitectura del Módulo

El módulo sigue una arquitectura en capas dentro del patrón MVC de Spring:

```
com.irfeyal.asistencia/
├── modelo/           # Entidades JPA (Asistencia, Clase)
├── dao/              # Repositorios Spring Data JPA
├── dto/              # Data Transfer Objects
│   └── auth/         # DTOs de autenticación (LoginRequest, LoginResponse)
├── mapper/           # Mapstruct alternativo manual (Entity ↔ DTO)
├── interfaces/       # Interfaces de servicio
├── servicio/         # Implementaciones de lógica de negocio
├── controlador/      # Controladores REST (AsistenciaController, AuthController)
├── security/         # Spring Security: JWT provider, filter, UserDetailsService, SecurityConfig
├── exception/        # @ControllerAdvice global + ResourceNotFoundException
├── config/           # OpenApiConfig (Swagger/OpenAPI con esquema Bearer JWT)
```

### Dependencias con otros módulos

| Módulo | Entidades usadas | Propósito |
|---|---|---|
| `matricula` | `Estudiante` | Identificar estudiantes |
| `parametrizacionacademica` | `Asignatura`, `Curso`, `Modalidad`, `Paralelo`, `Periodo` | Contexto académico |
| `rolseguridad` | `Empleado`, `Persona`, `Usuario`, `Rol`, `RolUsuario` | Autenticación y roles |

---

## API REST - Endpoints

### Autenticación

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/auth/login` | Inicia sesión y obtiene token JWT |

### Asistencias

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/asistencia/listarasistencia` | Lista paginada de asistencias (soporta `?page=0&size=10`) |
| `POST` | `/asistencia/asistenciasave` | Crea una nueva asistencia |
| `PUT` | `/asistencia/updateasistencia/{id}` | Actualiza estado de asistencia |
| `DELETE` | `/asistencia/asistenciadelete/{id}` | Elimina una asistencia |
| `GET` | `/asistencia/buscaractualizar/{...}` | Busca asistencias con filtros |
| `GET` | `/asistencia/buscarAsistencia/{...}` | Busca asistencias por filtros completos |

### Clases

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/asistencia/listarclase` | Lista todas las clases |
| `POST` | `/asistencia/clasesave` | Crea una nueva clase |
| `PUT` | `/asistencia/claseactualizar/{id}` | Actualiza una clase |
| `GET` | `/asistencia/claseingresada` | Última clase registrada |
| `GET` | `/asistencia/mostrarfechasdefaltas/{...}` | Fechas de faltas de un estudiante |
| `GET` | `/asistencia/validarclass/{...}` | Valida si una clase ya existe |

### Catálogos (con filtro por docente)

| Método | Endpoint |
|---|---|
| `GET` | `/asistencia/Periodos/{idEmpleado}` |
| `GET` | `/asistencia/modalidades/{idEmpleado}/{periodo}` |
| `GET` | `/asistencia/cursos/{idEmpleado}/{periodo}/{idMod}` |
| `GET` | `/asistencia/paralelos/{idEmpleado}/{periodo}/{modalidad}/{idCurso}` |
| `GET` | `/asistencia/asignaturas/{empleado}/{idPeriodo}/{modalidad}/{idCurso}/{idParalelo}` |
| `GET` | `/asistencia/Curso` |
| `GET` | `/asistencia/Paralelo` |
| `GET` | `/asistencia/Modalidad` |
| `GET` | `/asistencia/Periodo` |
| `GET` | `/asistencia/asignaturas` |

### Estudiantes

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/asistencia/asist/{id}` | Obtiene estudiante por ID |
| `GET` | `/asistencia/buscarestudianteporcedula/{ced}` | Busca estudiante por cédula |
| `GET` | `/asistencia/buscarestudianteid/{id}` | Información detallada del estudiante |
| `GET` | `/asistencia/buscarestudiantesdeuncurso/{id}` | Estudiantes de un curso |
| `GET` | `/asistencia/filtrosdelaasistencia/{...}` | Filtra estudiantes por modalidad, período, paralelo, asignatura, curso |

### Reportes PDF

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/asistencia/exportInvoice/{...}` | Reporte individual por estudiante |
| `GET` | `/asistencia/exportInvoicecurso/{...}` | Reporte por curso |

---

## Autenticación JWT

Todos los endpoints (excepto `/auth/login` y Swagger) requieren un token JWT en el header `Authorization`:

```
Authorization: Bearer <token>
```

### Obtener token

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "123456"}'
```

Respuesta:

```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "type": "Bearer",
    "userId": 1,
    "username": "admin",
    "roles": ["Administrador"],
    "nombre": "Admin",
    "apellido": "Sistema"
  }
}
```

---

## Swagger / OpenAPI

La documentación interactiva está disponible en:

- **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui.html`
- **OpenAPI spec**: `http://localhost:8080/api/v1/v3/api-docs`

Incluye autenticación Bearer JWT — haz clic en "Authorize" e ingresa tu token.

---

## Fragmentos Destacados

### 1. DTO con `ApiResponse<T>` tipado

```java
@GetMapping("/asistencia/{id}")
public ResponseEntity<ApiResponse<AsistenciaDTO>> mostrarEstudiante(@PathVariable Long id) {
    Estudiante estudiante = estudianteService.findById(id);
    if (estudiante == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.notFound("El id del estudiante " + id + " no existe"));
    }
    return ResponseEntity.ok(ApiResponse.ok(estudiante));
}
```

### 2. `@ControllerAdvice` global

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.notFound(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
            .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.badRequest(errors));
    }
}
```

### 3. Mapper Entity ↔ DTO

```java
@Component
public class AsistenciaMapper {

    public AsistenciaDTO toDto(Asistencia entity) {
        if (entity == null) return null;
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setIdAsistencia(entity.getIdAsistencia());
        dto.setEstadoAsis(entity.getEstadoAsis());
        dto.setIdClase(entity.getIdClase().getIdClase());
        dto.setIdEstudiante(entity.getIdEstudiante().getIdEstudiante());
        // Campos computados desde relaciones
        dto.setNombre(entity.getIdEstudiante().getIdPersona().getNombre());
        dto.setApellido(entity.getIdEstudiante().getIdPersona().getApellido());
        return dto;
    }

    public Asistencia toEntity(AsistenciaDTO dto) {
        if (dto == null) return null;
        Asistencia entity = new Asistencia();
        entity.setIdAsistencia(dto.getIdAsistencia());
        entity.setEstadoAsis(dto.getEstadoAsis());
        entity.setIdClase(new Clase(dto.getIdClase()));
        entity.setIdEstudiante(new Estudiante(dto.getIdEstudiante()));
        return entity;
    }
}
```

### 4. Spring Security + JWT

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .anyRequest().authenticated().and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
```

### 5. Reportes PDF con JasperReports

```java
public ResponseEntity<ByteArrayResource> exportInvoice(...) {
    final JasperReport report = (JasperReport) JRLoader.loadObject(file);
    final Map<String, Object> parameters = new HashMap<>();
    parameters.put("persoNom", estudiante.getIdPersona().getNombre());
    parameters.put("ds", new JRBeanCollectionDataSource(listaAsistencias));
    JasperPrint jPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
    byte[] reporte = JasperExportManager.exportReportToPdf(jPrint);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(new ByteArrayResource(reporte));
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
| **Arquitectura en capas** | Separación clara: modelo → dao → servicio → controlador |
| **DTOs con mappers** | `AsistenciaDTO`/`ClaseDTO` + `AsistenciaMapper`/`ClaseMapper` separan API de persistencia |
| **Manejo global de errores** | `@ControllerAdvice` centraliza errores 400, 404, 500 con `ApiResponse<T>` tipado |
| **Autenticación JWT** | Spring Security + JJWT con tokens Bearer y roles |
| **Validación declarativa** | `@Valid` + `@NotNull` en DTOs; `MethodArgumentNotValidException` capturado globalmente |
| **Paginación** | `Pageable` de Spring Data en endpoints de listado |
| **Lazy loading** | `FetchType.LAZY` en relaciones JPA para optimizar consultas |
| **Consultas nativas optimizadas** | JPQL nativo con JOINS para consultas complejas multi-tabla |
| **Control de acceso por roles** | Validación de roles (Administrador vs Docente) en generación de reportes |
| **Documentación interactiva** | Swagger UI con esquema Bearer JWT |
| **Tests automatizados** | Tests unitarios (mapper, service) y de integración (controller) |
| **Contenedorización** | Dockerfile multi-stage + docker-compose con PostgreSQL |

### Paquetes y propósito

| Paquete | Propósito |
|---|---|
| `modelo/` | Entidades JPA |
| `dao/` | Repositorios Spring Data con consultas nativas |
| `dto/` | Data Transfer Objects (entrada/salida de API) |
| `mapper/` | Conversión Entity ↔ DTO |
| `interfaces/` | Contratos de servicio |
| `servicio/` | Lógica de negocio + JasperReports |
| `controlador/` | Endpoints REST |
| `security/` | JWT, filtros, configuración Spring Security |
| `exception/` | Manejador global de excepciones |
| `config/` | Configuración OpenAPI/Swagger |

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
spring.datasource.password=${DB_PASSWORD:your_password}
spring.jpa.hibernate.ddl-auto=update

jwt.secret=${JWT_SECRET:IRFEYAL_SECRET_KEY_2024_CHANGE_IN_PRODUCTION_256_BITS}
jwt.expiration=${JWT_EXPIRATION:86400000}
```

### Ejecutar

```bash
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080/api/v1/asistencia/`

### Swagger UI

`http://localhost:8080/api/v1/swagger-ui.html`

---

## Mejoras y Correcciones

Este módulo fue auditado y corregido como parte de su preparación para portafolio. Las mejoras aplicadas incluyen:

| Categoría | Corrección |
|-----------|-----------|
| **Null Safety** | Corrección de null checks en `Asistencia.java` (revisaban campo incorrecto), `Optional.get()` con verificación `isPresent()`, validación de relaciones antes de dereferenciar |
| **Seguridad** | JWT secret y contraseña BD ahora vía variables de entorno (`${JWT_SECRET}`, `${DB_PASSWORD}`), eliminado `@CrossOrigin` redundante, authorities con prefijo `ROLE_` |
| **REST API** | PUT endpoints corrregidos a HTTP 200 OK (antes retornaban 201 Created), fechas en endpoints PDF con `@DateTimeFormat` y manejo de `ParseException` con 400 Bad Request |
| **JasperReports** | Reemplazo de `ResourceUtils.getFile()` por `ClassPathResource` (funciona desde JAR), parámetros default para evitar missing-parameter en reportes |
| **Base de datos** | `@OneToOne` corregido a `@ManyToOne` en relaciones Modalidad/Periodo, nombres de columna explícitos con `@JoinColumn` |
| **Consultas nativas** | Query `listarasignaturaasistencia` corregida para usar los 5 parámetros de filtrado, nombres de columna actualizados en todas las queries |
| **Código muerto** | Eliminada interfaz vacía `PeriodoServices`, método no usado `buscarasistencia` en DAO, `@ResponseStatus` redundantes en `@ControllerAdvice` |
| **Calidad** | Renombrado `ParaleloRespository` → `ParaleloRepository` (typo), eliminación de imports no usados |

### Tests

```bash
mvn test
```
**17 tests, 0 fallos.** Incluye unitarios (mapper, service), integración (controller vía `@WebMvcTest`) y carga de contexto Spring Boot con H2.

---

## Docker

### Construir y ejecutar

```bash
docker-compose up --build
```

Esto levanta:
- **app** (`irfeyal-asistencia`) en puerto `8080`
- **db** (`postgres:14`) en puerto `5432`

### Variables de entorno

| Variable | Propósito |
|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexión a PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuario BD |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña BD |
| `JWT_SECRET` | Clave secreta para firmar tokens JWT |
| `JWT_EXPIRATION` | Tiempo de expiración del token (ms) |

---

## Tests

```bash
mvn test
```

Incluye:
- **Unitarios**: `AsistenciaMapperTest` (4 tests), `AsistenciaServiceTest` (5 tests)
- **Integración**: `AsistenciaControllerTest` (7 tests vía `@WebMvcTest`)
- **Contexto**: `BackendIrfeyalApplicationTests` (carga de contexto con H2)

---

## Licencia

Este repositorio contiene el **módulo de asistencia** del sistema educativo IRFEYAL, extraído y adaptado con fines de portafolio. El código completo del sistema IRFEYAL es privado y pertenece a sus respectivos dueños.

**Lo que demuestra este módulo:**
- Arquitectura en capas con Spring Boot 2.6 + Spring Security + JWT
- Persistencia JPA con consultas nativas optimizadas
- Generación de reportes PDF con JasperReports
- API REST documentada con OpenAPI/Swagger
- Tests unitarios y de integración con JUnit 5 + Mockito
- Contenedorización con Docker y docker-compose
