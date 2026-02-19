package com.generation.Bloom_Studio.dto;

import java.util.List;

public class CrearPedidoDto {
    // Una lista de items (Renglones del carrito)
    private List<ProductoPedidoDto> productos;

    public List<ProductoPedidoDto> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoPedidoDto> productos) {
        this.productos = productos;
    }

    // Clase interna (static) para definir cada item
    public static class ProductoPedidoDto {
        private Long idInventario;
        private Integer cantidad;

        public Long getIdInventario() { return idInventario; }
        public void setIdInventario(Long idInventario) { this.idInventario = idInventario; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}
