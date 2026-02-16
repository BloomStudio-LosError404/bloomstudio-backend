package org.generation.blood_studio.repository;

import org.generation.blood_studio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar un usuario por email (Para que jale el Login)
    // Devuelve un Optional para evitar errores si el email no existe
    Optional<Usuario> findByEmail(String email);

    // Verificamos si un email ya existe
    // Devuelve true o false
    boolean existsByEmail(String email);
}