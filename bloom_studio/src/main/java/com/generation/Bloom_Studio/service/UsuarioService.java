package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.exceptions.ResourceNotFoundException;
import com.generation.Bloom_Studio.exceptions.UserAlreadyExistsException;
import com.generation.Bloom_Studio.model.Rol;
import com.generation.Bloom_Studio.model.Usuario;
import com.generation.Bloom_Studio.repository.RolRepository;
import com.generation.Bloom_Studio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyección de dependencias por constructor
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. OBTENER TODOS LOS USUARIOS
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // 2. OBTENER USUARIO POR ID
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    // 3. REGISTRAR UN NUEVO USUARIO
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // 1. Validación de Email
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new UserAlreadyExistsException("El email " + usuario.getEmail() + " ya está registrado.");
        }

        // 2. Asignación de Rol
        if (usuario.getRol() == null) {
            Rol rolCliente = rolRepository.findByNombreRol("CLIENTE")
                    .orElseThrow(() -> new ResourceNotFoundException("Error: El rol 'CLIENTE' no existe."));
            usuario.setRol(rolCliente);
        }

        // 3. Encriptamos la CONTRASEÑA
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        return usuarioRepository.save(usuario);
    }

    // 4. ELIMINAR USUARIO
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}