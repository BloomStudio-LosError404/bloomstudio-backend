package com.generation.Bloom_Studio.dto;

import java.math.BigDecimal;
import java.util.List;

public class CatalogProductDTO {
    private Long id;
    private String sku;

    private String nombre;
    private String descripcion;

    private String categoria;              // categoría principal
    private List<String> categorias;       // todas las categorías

    private List<String> colores;
    private List<String> tallas;

    private BigDecimal precio;

    private double rating;
    private int reviews;

    private long stockTotal;

    private List<String> etiquetas;

    private ImagenDTO imagen;

    public static class ImagenDTO {
        private String src;
        private String alt;

        public ImagenDTO() {}

        public ImagenDTO(String src, String alt) {
            this.src = src;
            this.alt = alt;
        }

        public String getSrc() { return src; }
        public void setSrc(String src) { this.src = src; }

        public String getAlt() { return alt; }
        public void setAlt(String alt) { this.alt = alt; }
    }

    public CatalogProductDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public List<String> getCategorias() { return categorias; }
    public void setCategorias(List<String> categorias) { this.categorias = categorias; }

    public List<String> getColores() { return colores; }
    public void setColores(List<String> colores) { this.colores = colores; }

    public List<String> getTallas() { return tallas; }
    public void setTallas(List<String> tallas) { this.tallas = tallas; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getReviews() { return reviews; }
    public void setReviews(int reviews) { this.reviews = reviews; }

    public long getStockTotal() { return stockTotal; }
    public void setStockTotal(long stockTotal) { this.stockTotal = stockTotal; }

    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; }

    public ImagenDTO getImagen() { return imagen; }
    public void setImagen(ImagenDTO imagen) { this.imagen = imagen; }
}