package com.generation.Bloom_Studio.dto;

import com.generation.Bloom_Studio.model.EstadoProducto;

import java.math.BigDecimal;

public class ProductListDTO {

    private Long id;
    private String sku;
    private String nombre;
    private BigDecimal precio;     // <- CAMBIO
    private String imgUrl;
    private EstadoProducto estadoProducto;
    private Boolean estatus;
    private Long stockTotal;

    public ProductListDTO(Long id, String sku, String nombre, BigDecimal precio, String imgUrl,
                          EstadoProducto estadoProducto, Boolean estatus, Long stockTotal) { // <- CAMBIO
        this.id = id;
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
        this.imgUrl = imgUrl;
        this.estadoProducto = estadoProducto;
        this.estatus = estatus;
        this.stockTotal = stockTotal;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; } // <- CAMBIO
    public String getImgUrl() { return imgUrl; }
    public EstadoProducto getEstadoProducto() { return estadoProducto; }
    public Boolean getEstatus() { return estatus; }
    public Long getStockTotal() { return stockTotal; } // <- CAMBIO
}