package com.irfeyal.asistencia.dto.auth;

import java.util.List;

public class LoginResponse {

    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private List<String> roles;
    private String nombre;
    private String apellido;

    public LoginResponse() {
    }

    public LoginResponse(String token, Long userId, String username, List<String> roles,
                         String nombre, String apellido) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
