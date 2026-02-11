package com.generation.Bloom_Studio.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "categorias")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_categoria")
    private Integer idCategoria;

    @Column (name = "nombre_categoria", length = 50, nullable = false)
    private String nombreCategoria;

    @Column (name ="estatus")
    private Boolean estatus = true;

    @Column (name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column (name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public Category(Integer idCategoria, String nombreCategoria, Boolean estatus, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.estatus = estatus;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Category() {
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
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

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "Category{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", estatus=" + estatus +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return Objects.equals(idCategoria, category.idCategoria) && Objects.equals(nombreCategoria, category.nombreCategoria) && Objects.equals(estatus, category.estatus) && Objects.equals(fechaCreacion, category.fechaCreacion) && Objects.equals(fechaActualizacion, category.fechaActualizacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoria, nombreCategoria, estatus, fechaCreacion, fechaActualizacion);
    }
}

