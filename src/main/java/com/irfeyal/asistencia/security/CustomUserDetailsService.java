package com.irfeyal.asistencia.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.irfeyal.rolseguridad.dao.RolUsuarioDAO;
import com.irfeyal.rolseguridad.dao.UsuarioDAO;
import com.irfeyal.rolseguridad.modelo.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private RolUsuarioDAO rolUsuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByUsuario(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Usuario no encontrado: " + username));

        if (Boolean.FALSE.equals(usuario.getEstUsuario())) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        List<String> roles = rolUsuarioDAO.validacionadmin(usuario.getIdUsuario());
        List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new User(
            usuario.getUsuario(),
            usuario.getContrasenia(),
            authorities);
    }

}
