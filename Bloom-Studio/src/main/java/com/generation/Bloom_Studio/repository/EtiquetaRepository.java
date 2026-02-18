package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Category;
import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.model.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {
    List<Etiqueta> findByNombreEtiqueta(String nombreEtiqueta);
    List<Etiqueta> findByEstatus(Boolean estatus);
}

