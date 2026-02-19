package com.generation.Bloom_Studio.security;

import com.generation.Bloom_Studio.model.Usuario;
import com.generation.Bloom_Studio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("--> Intentando login con email: " + email); // DEBUG 1

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("--> ERROR: Usuario no encontrado en BD"); // DEBUG 2
                    return new UsernameNotFoundException("Usuario no encontrado con email: " + email);
                });

        System.out.println("--> Usuario encontrado: " + usuario.getNombre()); // DEBUG 3
        System.out.println("--> Contraseña en BD: " + usuario.getContrasena()); // DEBUG 4

        // Verificamos el rol para evitar NullPointer
        if (usuario.getRol() == null) {
            System.out.println("--> ERROR CRÍTICO: El usuario no tiene ROL asignado"); // DEBUG 5
            throw new UsernameNotFoundException("Usuario sin rol asignado");
        }

        System.out.println("--> Rol del usuario: " + usuario.getRol().getNombreRol()); // DEBUG 6

        String nombreRol = "ROLE_" + usuario.getRol().getNombreRol().toUpperCase();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(nombreRol);

        return new User(
                usuario.getEmail(),
                usuario.getContrasena(),
                Collections.singletonList(authority)
        );
    }
}
