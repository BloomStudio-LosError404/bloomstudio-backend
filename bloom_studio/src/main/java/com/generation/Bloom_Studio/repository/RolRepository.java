package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    // Método personalizado para buscar un rol por su nombre
    // Spring Boot crea el SQL: SELECT * FROM roles WHERE nombre_rol = ?
    Optional<Rol> findByNombreRol(String nombreRol);
}