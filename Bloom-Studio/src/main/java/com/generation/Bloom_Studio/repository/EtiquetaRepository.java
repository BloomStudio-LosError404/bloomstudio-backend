package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Category;
import com.generation.Bloom_Studio.model.Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtiquetaRepository extends JpaRepository<Colors, Long> {

    List<Category> findByNombreEtiqueta(String nombreEtiqueta);
    List<Colors> findByEstatus(Boolean estatus);
}
