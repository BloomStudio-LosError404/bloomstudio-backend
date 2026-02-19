package com.generation.Bloom_Studio.dto;

import com.generation.Bloom_Studio.model.EstadoProducto;

import java.math.BigDecimal;
import java.util.List;

public class ProductResponseDTO {
    private Long id;
    private String sku;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imgUrl;
    private EstadoProducto estadoProducto;
    private Boolean estatus;
    private long stockTotal;
    List<String> categoriaNombres;
    List<String> etiquetaNombres;

    public ProductResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public BigDecimal getPrecio() {
        return precio;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public EstadoProducto getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(EstadoProducto estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public long getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(long stockTotal) {
    this.stockTotal = stockTotal;
    }

    public List<String> getCategoriaNombres() {
        return categoriaNombres;
    }

    public void setCategoriaNombres(List<String> categoriaNombres) {
        this.categoriaNombres = categoriaNombres;
    }

    public List<String> getEtiquetaNombres() {
        return etiquetaNombres;
    }

    public void setEtiquetaNombres(List<String> etiquetaNombres) {
        this.etiquetaNombres = etiquetaNombres;
    }
}
