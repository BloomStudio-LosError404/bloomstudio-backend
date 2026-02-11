package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Products, Long> {

    Optional<Products> findBySku(String sku);

    boolean existsBySku (String sku);

    List<Products> findByEstatusTrue();
}
