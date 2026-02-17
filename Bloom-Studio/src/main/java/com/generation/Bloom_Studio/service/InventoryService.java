package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.dto.InventoryResponseDTO;
import com.generation.Bloom_Studio.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InventoryService {
    InventoryResponseDTO crearOActualizarCantidad(Long productoId, Long colorId, Long tallaId, Integer cantidad);
    InventoryResponseDTO incrementar(Long inventarioId, Integer delta);
    InventoryResponseDTO decrementar(Long inventarioId, Integer delta);
    InventoryResponseDTO obtenerPorId(Long inventarioId);
    List<InventoryResponseDTO> listarPorProducto(Long productoId);
    List<InventoryResponseDTO> listarTodos();

}

