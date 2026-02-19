package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Talla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TallaRepository extends JpaRepository<Talla, Long> {


    // Buscar por nombre
    Talla findByNombreTalla(String nombreTalla);

    // Traer solo tallas activas (si usas soft delete)
    List<Talla> findByEstatusTrue();

}
