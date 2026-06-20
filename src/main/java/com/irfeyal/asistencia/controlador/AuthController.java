package com.irfeyal.asistencia.controlador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.irfeyal.asistencia.dto.ApiResponse;
import com.irfeyal.asistencia.dto.auth.LoginRequest;
import com.irfeyal.asistencia.dto.auth.LoginResponse;
import com.irfeyal.asistencia.security.JwtTokenProvider;
import com.irfeyal.rolseguridad.dao.RolUsuarioDAO;
import com.irfeyal.rolseguridad.dao.UsuarioDAO;
import com.irfeyal.rolseguridad.modelo.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Login y generación de tokens JWT")
public class AuthController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private RolUsuarioDAO rolUsuarioDAO;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica usuario y contraseña, retorna token JWT")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioDAO.findByUsuario(request.getUsername());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(401, "Credenciales inválidas", "Usuario o contraseña incorrectos"));
        }

        Usuario usuario = usuarioOpt.get();

        if (Boolean.FALSE.equals(usuario.getEstUsuario())) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(401, "Usuario inactivo", "El usuario se encuentra desactivado"));
        }

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), usuario.getContrasenia());

        if (!passwordMatches) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(401, "Credenciales inválidas", "Usuario o contraseña incorrectos"));
        }

        List<String> roles = rolUsuarioDAO.validacionadmin(usuario.getIdUsuario());
        String token = jwtTokenProvider.generateToken(
            usuario.getIdUsuario(),
            usuario.getUsuario(),
            roles);

        String nombre = usuario.getEmpleado() != null && usuario.getEmpleado().getPersona() != null
            ? usuario.getEmpleado().getPersona().getNombre() : "";
        String apellido = usuario.getEmpleado() != null && usuario.getEmpleado().getPersona() != null
            ? usuario.getEmpleado().getPersona().getApellido() : "";

        LoginResponse loginResponse = new LoginResponse(
            token, usuario.getIdUsuario(), usuario.getUsuario(),
            roles, nombre, apellido);

        return ResponseEntity.ok(ApiResponse.ok(loginResponse));
    }

}
