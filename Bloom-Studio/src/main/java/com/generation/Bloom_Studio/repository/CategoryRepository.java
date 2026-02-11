package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByNombreCategoria(String nombreCategoria);
    List<Category> findByEstatus(Boolean estatus);

}
