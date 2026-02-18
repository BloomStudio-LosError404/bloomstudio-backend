package com.generation.Bloom_Studio.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreateProductRequestDTO {
    private String sku;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    private List<Long> categoriaIds;
    private List<Long> etiquetaIds;

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public List<Long> getCategoriaIds() { return categoriaIds; }
    public void setCategoriaIds(List<Long> categoriaIds) { this.categoriaIds = categoriaIds; }

    public List<Long> getEtiquetaIds() { return etiquetaIds; }
    public void setEtiquetaIds(List<Long> etiquetaIds) { this.etiquetaIds = etiquetaIds; }
}
