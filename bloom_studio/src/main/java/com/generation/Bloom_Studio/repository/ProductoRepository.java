package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.dto.ProductListDTO;
import com.generation.Bloom_Studio.model.Products;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Products, Long> {

    Optional<Products> findBySku(String sku);

    boolean existsBySku (String sku);

    List<Products> findByEstatusTrue();
    List<Products> findAll();

    @EntityGraph(attributePaths = {"categorias", "etiquetas"})
    Optional<Products> findWithRelacionesById(Long id);

    @EntityGraph(attributePaths = {"categorias", "etiquetas"})
    @Query("SELECT p FROM Products p WHERE p.estatus = true")
    List<Products> findActivosWithRelaciones();

    @Query("""
    SELECT new com.generation.Bloom_Studio.dto.ProductListDTO(
        p.id,
        p.sku,
        p.nombre,
        p.precio,
        p.imgUrl,
        p.estadoProducto,
        p.estatus,
        COALESCE(SUM(i.cantidad), 0L)
    )
    FROM Products p
    LEFT JOIN Inventory i ON i.products = p
    GROUP BY p.id, p.sku, p.nombre, p.precio, p.imgUrl, p.estadoProducto, p.estatus
""")
    List<ProductListDTO> listarTodosConStock();


    @Query("""
    SELECT new com.generation.Bloom_Studio.dto.ProductListDTO(
        p.id,
        p.sku,
        p.nombre,
        p.precio,
        p.imgUrl,
        p.estadoProducto,
        p.estatus,
        COALESCE(SUM(i.cantidad), 0L)
    )
    FROM Products p
    LEFT JOIN Inventory i ON i.products = p
    WHERE p.estatus = true
    GROUP BY p.id, p.sku, p.nombre, p.precio, p.imgUrl, p.estadoProducto, p.estatus
""")
    List<ProductListDTO> listarActivosConStock();


}
