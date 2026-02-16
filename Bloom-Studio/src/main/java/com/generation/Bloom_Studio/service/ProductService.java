package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.model.Products;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    Products crearProducto(Products products);
    Products obtenerProductoId (Long id);
    List<Products> listaProductosActivos();
    Products actualizarProducto(Long id, Products products);
    void eliminarProducto (Long id);
}
