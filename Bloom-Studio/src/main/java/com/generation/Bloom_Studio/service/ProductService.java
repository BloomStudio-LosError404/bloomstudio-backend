package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.dto.CatalogProductDTO;
import com.generation.Bloom_Studio.dto.CreateProductRequestDTO;
import com.generation.Bloom_Studio.dto.ProductListDTO;
import com.generation.Bloom_Studio.dto.ProductResponseDTO;
import com.generation.Bloom_Studio.model.EstadoProducto;
import com.generation.Bloom_Studio.model.Products;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    Products crearProductoConRelaciones(CreateProductRequestDTO dto, String imgUrl);

    Products crearProducto(Products products);
    Products obtenerProductoId (Long id);
    List<Products> listaProductosActivos();
    Products actualizarProducto(Long id, Products products);
    void eliminarProducto (Long id);

    Products cambiarEstadoProducto(Long id, EstadoProducto nuevoEstado);

    ProductResponseDTO obtenerProductoConStock(Long id);

    List<Products> listarTodos();

    List<ProductListDTO> listarTodosConStock();

    List<ProductListDTO> listarActivosConStock();

    List<CatalogProductDTO> listarCatalogo();

    Products actualizarCategoriasEtiquetas(Long productId, List<Long> categoriaIds, List<Long> etiquetaIds);

}
