package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.model.Inventory;
import com.generation.Bloom_Studio.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/inventario")
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/variante")
    public ResponseEntity<Inventory> setCantidad(@RequestBody Map<String, Object>body){
        Long productoId = Long.valueOf(body.get("productoId").toString());
        Long colorId = Long.valueOf(body.get("colorId").toString());
        Long tallaId = Long.valueOf(body.get("colorID").toString());
        Integer cantidad = Integer.valueOf(body.get("cantidad").toString());

        Inventory inventory = inventoryService.crearOActualizarCantidad(productoId,colorId,tallaId,cantidad);
        return ResponseEntity.ok(inventory);
    }

    @PatchMapping("/{id}/incrementar")
    public ResponseEntity<Inventory> incrementar(@PathVariable Long id, @RequestBody Map<String, Object>body){
        Integer delta = Integer.valueOf(body.get("delta").toString());
        return ResponseEntity.ok(inventoryService.incrementar(id,delta));
    }

    @PatchMapping("/{id}/decrementar")
    public ResponseEntity<Inventory> decrementar(@PathVariable Long id,@RequestBody Map<String, Object>body){
        Integer delta = Integer.valueOf(body.get("delta").toString());
        return ResponseEntity.ok(inventoryService.decrementar(id,delta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> obtener(@PathVariable Long id){
        return ResponseEntity.ok(inventoryService.obtenerPorId(id));
    }

   @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Inventory>> listaPorProducto(@PathVariable Long productoId){
        return ResponseEntity.ok(inventoryService.listaPorProducto(productoId));
    }



}
