package com.generation.Bloom_Studio.dto;

public class InventoryResponseDTO {

    private Long idInventario;

    private Long productoId;
    private String sku;
    private String nombreProducto;

    private Long colorId;
    private String nombreColor;

    private Long tallaId;
    private String nombreTalla;

    private Integer cantidad;

    public InventoryResponseDTO() {}

    public Long getIdInventario() { return idInventario; }
    public void setIdInventario(Long idInventario) { this.idInventario = idInventario; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public Long getColorId() { return colorId; }
    public void setColorId(Long colorId) { this.colorId = colorId; }

    public String getNombreColor() { return nombreColor; }
    public void setNombreColor(String nombreColor) { this.nombreColor = nombreColor; }

    public Long getTallaId() { return tallaId; }
    public void setTallaId(Long tallaId) { this.tallaId = tallaId; }

    public String getNombreTalla() { return nombreTalla; }
    public void setNombreTalla(String nombreTalla) { this.nombreTalla = nombreTalla; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
