package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.dto.InventoryResponseDTO;
import com.generation.Bloom_Studio.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.generation.Bloom_Studio.exceptions.inventory.InventoryBadRequestException;



import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventario")
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/variante")
    public ResponseEntity<InventoryResponseDTO> setCantidad(@RequestBody Map<String, Object> body) {

        // Validación mínima para evitar NPE por llaves mal escritas
        if (body.get("productoId") == null || body.get("colorId") == null || body.get("tallaId") == null || body.get("cantidad") == null) {
            throw new InventoryBadRequestException("Body inválido. Se requieren: productoId, colorId, tallaId, cantidad.");
        }

        Long productoId = Long.valueOf(body.get("productoId").toString());
        Long colorId = Long.valueOf(body.get("colorId").toString());
        Long tallaId = Long.valueOf(body.get("tallaId").toString());
        Integer cantidad = Integer.valueOf(body.get("cantidad").toString());

        InventoryResponseDTO dto = inventoryService.crearOActualizarCantidad(productoId, colorId, tallaId, cantidad);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/incrementar")
    public ResponseEntity<InventoryResponseDTO> incrementar(@PathVariable Long id, @RequestBody Map<String, Object> body) {

        if (body.get("delta") == null) {
            throw new InventoryBadRequestException("Body inválido. Se requiere: delta.");
        }

        Integer delta = Integer.valueOf(body.get("delta").toString());
        InventoryResponseDTO dto = inventoryService.incrementar(id, delta);

        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/decrementar")
    public ResponseEntity<InventoryResponseDTO> decrementar(@PathVariable Long id, @RequestBody Map<String, Object> body) {

        if (body.get("delta") == null) {
            throw new InventoryBadRequestException("Body inválido. Se requiere: delta.");
        }

        Integer delta = Integer.valueOf(body.get("delta").toString());
        InventoryResponseDTO dto = inventoryService.decrementar(id, delta);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.obtenerPorId(id));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<InventoryResponseDTO>> listaPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(inventoryService.listarPorProducto(productoId));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> listarTodos() {
        return ResponseEntity.ok(inventoryService.listarTodos());
    }

}

