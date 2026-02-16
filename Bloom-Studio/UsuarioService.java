package org.generation.blood_studio.service;

import org.generation.blood_studio.exception.ResourceNotFoundException;
import org.generation.blood_studio.exception.UserAlreadyExistsException;
import org.generation.blood_studio.model.Rol;
import org.generation.blood_studio.model.Usuario;
import org.generation.blood_studio.repository.RolRepository;
import org.generation.blood_studio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    // Inyección de dependencias por constructor
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
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
    @Transactional // Asegura que si algo falla, no se guarde nada a medias
    public Usuario registrarUsuario(Usuario usuario) {

        // A. Validar que el email no exista ya jsjs
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new UserAlreadyExistsException("El email " + usuario.getEmail() + " ya está registrado.");
        }

        // B. Asignar Rol por defecto (CLIENTE)
        if (usuario.getRol() == null) {
            Rol rolCliente = rolRepository.findByNombreRol("CLIENTE")
                    .orElseThrow(() -> new ResourceNotFoundException("Error: El rol 'CLIENTE' no existe."));
            usuario.setRol(rolCliente);
        }

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