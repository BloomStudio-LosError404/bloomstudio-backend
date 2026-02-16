package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InventoryService {

    Inventory crearOActualizarCantidad(Long productoId, Long colorId, Long tallaId, Integer cantidad);
    Inventory incrementar(Long inventarioId, Integer delta);
    Inventory decrementar(Long inventarioId, Integer delta);
    Inventory obtenerPorId(Long inventarioId);
    List<Inventory> listaPorProducto(Long productoId);
}
