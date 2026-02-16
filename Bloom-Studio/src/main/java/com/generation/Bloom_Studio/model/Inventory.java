package com.generation.Bloom_Studio.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Products products;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", nullable = false)
    private Colors color;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_talla", nullable = false)
    private Talla tallas;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad = 0;

    @UpdateTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;


    public Inventory(Long id, Products products, Colors color, Talla tallas, Integer cantidad, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.products = products;
        this.color = color;
        this.tallas = tallas;
        this.cantidad = cantidad;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }


    public void incrementar(int delta) {
        if (delta < 0) throw new IllegalArgumentException("Delta inválido.");
        this.cantidad = this.cantidad + delta;
    }

    public void decrementar(int delta) {
        if (delta < 0) throw new IllegalArgumentException("Delta inválido.");
        int nueva = this.cantidad - delta;
        if (nueva < 0) throw new IllegalArgumentException("Stock insuficiente.");
        this.cantidad = nueva;
    }

    public Inventory() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public Talla getTallas() {
        return tallas;
    }

    public void setTallas(Talla tallas) {
        this.tallas = tallas;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad){
        if(cantidad == null || cantidad <0){
            throw new IllegalArgumentException("La cantidad no puede ser nula ni negativa");
        }
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", products=" + products +
                ", color=" + color +
                ", tallas=" + tallas +
                ", cantidad=" + cantidad +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Inventory inventory)) return false;
        return Objects.equals(id, inventory.id) && Objects.equals(products, inventory.products) && Objects.equals(color, inventory.color) && Objects.equals(tallas, inventory.tallas) && Objects.equals(cantidad, inventory.cantidad) && Objects.equals(fechaCreacion, inventory.fechaCreacion) && Objects.equals(fechaActualizacion, inventory.fechaActualizacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, color, tallas, cantidad, fechaCreacion, fechaActualizacion);
    }
}




