package com.generation.Bloom_Studio.dto;

import com.generation.Bloom_Studio.model.EstadoProducto;

public class ProductEstadoRequestDTO {
    private EstadoProducto estadoProducto;

    public ProductEstadoRequestDTO() {}

    public EstadoProducto getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(EstadoProducto estadoProducto) {
        this.estadoProducto = estadoProducto;
    }
}
