package com.generation.Bloom_Studio.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "productos")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "sku", unique = true, length = 50)
    private String sku;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", nullable = false,precision = 10,scale = 2)
    private BigDecimal precio;

    @Column(name = "img_url", length = 500)
    private String imgUrl;

    @Convert(converter = EstadoProductoConverter.class)
    @Column(name = "estado_producto",nullable = false)
    private EstadoProducto estadoProducto = EstadoProducto.ACTIVO;

    @Column(name = "estatus")
    private Boolean estatus = true;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "producto_categoria",joinColumns = @JoinColumn(name = "id_producto"),inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private Set<Category> categorias = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "producto_etiqueta",joinColumns = @JoinColumn(name = "id_producto"),inverseJoinColumns = @JoinColumn(name = "id_etiqueta")
    )
    private Set<Etiqueta> etiquetas = new HashSet<>();

    public Products(Long id, String sku, String nombre, String descripcion, BigDecimal precio, String imgUrl, EstadoProducto estadoProducto, Boolean estatus, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.sku = sku;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imgUrl = imgUrl;
        this.estadoProducto = estadoProducto;
        this.estatus = estatus;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Products(){}

    public Set<Category> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Category> categorias) {
        this.categorias = categorias;
    }

    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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
        return "Products{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", imgUrl='" + imgUrl + '\'' +
                ", estadoProducto=" + estadoProducto +
                ", estatus=" + estatus +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", categorias=" + categorias +
                ", etiquetas=" + etiquetas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Products products)) return false;
        return Objects.equals(id, products.id) && Objects.equals(sku, products.sku) && Objects.equals(nombre, products.nombre) && Objects.equals(descripcion, products.descripcion) && Objects.equals(precio, products.precio) && Objects.equals(imgUrl, products.imgUrl) && estadoProducto == products.estadoProducto && Objects.equals(estatus, products.estatus) && Objects.equals(fechaCreacion, products.fechaCreacion) && Objects.equals(fechaActualizacion, products.fechaActualizacion) && Objects.equals(categorias, products.categorias) && Objects.equals(etiquetas, products.etiquetas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sku, nombre, descripcion, precio, imgUrl, estadoProducto, estatus, fechaCreacion, fechaActualizacion, categorias, etiquetas);
    }
}
