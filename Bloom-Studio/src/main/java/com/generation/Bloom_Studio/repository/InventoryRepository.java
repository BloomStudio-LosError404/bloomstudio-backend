package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository <Inventory, Long> {
    Optional<Inventory> findByProductoIdAndColorIdAndTallaId(Long productoId,Long colorId, Long tallaId );

    List<Inventory> findAllByProductoId(Long productoId);
    
}
