package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.dto.CatalogProductDTO;
import com.generation.Bloom_Studio.dto.ProductEstadoRequestDTO;
import com.generation.Bloom_Studio.dto.ProductListDTO;
import com.generation.Bloom_Studio.dto.ProductResponseDTO;
import com.generation.Bloom_Studio.exceptions.product.ProductBadRequestException;
import com.generation.Bloom_Studio.model.Products;
import com.generation.Bloom_Studio.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Products> crear(@RequestBody Products products){
        Products creado = productService.crearProducto(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ProductListDTO>> listarTodos() {
        return ResponseEntity.ok(productService.listarTodosConStock());
    }

    // ===== NUEVO: endpoint de catálogo para el frontend Shop =====
    @GetMapping("/catalogo")
    public ResponseEntity<List<CatalogProductDTO>> catalogo() {
        return ResponseEntity.ok(productService.listarCatalogo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> obtenerProductoId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.obtenerProductoConStock(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Products> cambiarEstado(@PathVariable Long id, @RequestBody ProductEstadoRequestDTO body) {
        if (body == null || body.getEstadoProducto() == null) {
            throw new ProductBadRequestException("Body inválido. Se requiere: estadoProducto.");
        }

        Products actualizado = productService.cambiarEstadoProducto(id, body.getEstadoProducto());
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping
    public ResponseEntity<List<ProductListDTO>> listarActivos() {
        return ResponseEntity.ok(productService.listarActivosConStock());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> actualizar(@PathVariable Long id, @RequestBody Products products){
        Products actualizado = productService.actualizarProducto(id, products);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto (@PathVariable Long id){
        productService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}