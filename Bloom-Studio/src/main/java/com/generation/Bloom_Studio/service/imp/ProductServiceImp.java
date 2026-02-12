package com.generation.Bloom_Studio.service.imp;

import com.generation.Bloom_Studio.model.Products;
import com.generation.Bloom_Studio.repository.ProductoRepository;
import com.generation.Bloom_Studio.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
    private final ProductoRepository productoRepository;

    public ProductServiceImp(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Products crearProducto(Products products) {
        if (products.getSku() != null && productoRepository.existsBySku(products.getSku())) {
            throw new IllegalArgumentException("El SKU ya existe.");
        }
        if (products.getNombre() == null || products.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (products.getPrecio() == null) {
            throw new IllegalArgumentException("El precio es obligatorio.");
        }
        products.setEstatus(true);

        return productoRepository.save(products);
    }

    @Override
    @Transactional(readOnly = true)
    public Products obtenerProductoId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Products> listaProductosActivos() {
        return productoRepository.findByEstatusTrue();
    }

    @Override
    public Products actualizarProducto(Long id, Products products) {
        Products existente = obtenerProductoId(id);

        if(products.getNombre() != null) existente.setNombre(products.getNombre());
        if(products.getDescripcion() != null )existente.setDescripcion(products.getDescripcion());
        if(products.getPrecio() != null) existente.setPrecio(products.getPrecio());
        if(products.getImgUrl() != null )existente.setImgUrl(products.getImgUrl());
        if(products.getEstadoProducto() != null) existente.setEstadoProducto(products.getEstadoProducto());

        if (products.getSku() != null && !products.getSku().equals(existente.getSku())) {
            if (productoRepository.existsBySku(products.getSku())) {
                throw new IllegalArgumentException("El SKU ya existe.");
            }
            existente.setSku(products.getSku());
        }

        return productoRepository.save(existente);
    }

    @Override
    public void eliminarProducto(Long id) {
        Products existente = obtenerProductoId(id);
        existente.setEstatus(false);
        productoRepository.save(existente);
    }

}
