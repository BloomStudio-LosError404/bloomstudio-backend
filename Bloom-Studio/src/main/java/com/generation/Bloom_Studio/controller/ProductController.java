package com.generation.Bloom_Studio.controller;

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

    @GetMapping("/{id}")
    public ResponseEntity<Products> obtenerId (@PathVariable Long id){
        Products encontrado = productService.obtenerProductoId(id);
        return ResponseEntity.ok(encontrado);
    }

    @GetMapping
    public ResponseEntity<List<Products>> listaActivos(){
        List<Products>products = productService.listaProductosActivos();
        return ResponseEntity.ok(products);
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

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> manejarSolicitudes (IllegalArgumentException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> menejarNoEncontrar (EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }



}
