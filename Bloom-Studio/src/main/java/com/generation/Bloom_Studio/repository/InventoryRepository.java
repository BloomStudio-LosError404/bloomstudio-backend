package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProducts_IdAndColor_IdAndTallas_Id(Long productoId, Long colorId, Long tallaId);

    @Query("""
        SELECT i FROM Inventory i
        JOIN FETCH i.products p
        JOIN FETCH i.color c
        JOIN FETCH i.tallas t
        WHERE p.id = :productoId
    """)
    List<Inventory> findAllByProductoIdWithJoins(@Param("productoId") Long productoId);

    @Query("""
        SELECT i FROM Inventory i
        JOIN FETCH i.products p
        JOIN FETCH i.color c
        JOIN FETCH i.tallas t
    """)
    List<Inventory> findAllWithJoins();

    @Query("SELECT COALESCE(SUM(i.cantidad), 0) FROM Inventory i WHERE i.products.id = :productoId")
    long sumCantidadByProductoId(@Param("productoId") Long productoId);

    // ===== NUEVO: stock por lista de productos (evita N+1) =====
    interface StockTotalRow {
        Long getProductoId();
        Long getStockTotal();
    }

    @Query("""
        SELECT i.products.id AS productoId, COALESCE(SUM(i.cantidad), 0) AS stockTotal
        FROM Inventory i
        WHERE i.products.id IN :productoIds
        GROUP BY i.products.id
    """)
    List<StockTotalRow> sumStockByProductoIds(@Param("productoIds") List<Long> productoIds);

    // ===== NUEVO: inventarios por lista de productos con joins =====
    @Query("""
        SELECT i FROM Inventory i
        JOIN FETCH i.products p
        JOIN FETCH i.color c
        JOIN FETCH i.tallas t
        WHERE p.id IN :productoIds
    """)
    List<Inventory> findAllByProductoIdsWithJoins(@Param("productoIds") List<Long> productoIds);
}