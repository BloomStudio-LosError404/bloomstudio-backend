package com.generation.Bloom_Studio.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "etiquetas")
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_etiqueta;

    @Column(nullable = false, length = 50)
    private String nombreEtiqueta;

    private Boolean estatus;

    @Column(updatable = false)
    private LocalDateTime fecha_creacion;

    private LocalDateTime fecha_actualizacion;

    // cocneccion con productos n:m

    @ManyToMany (mappedBy = "productos",cascade = CascadeType.ALL)
    private List<Products> productos;


    public Etiqueta() {
    }


    public Etiqueta(Long id_etiqueta, String nombreEtiqueta, Boolean estatus, LocalDateTime fecha_creacion, LocalDateTime fecha_actualizacion) {
        this.id_etiqueta = id_etiqueta;
        this.nombreEtiqueta = nombreEtiqueta;
        this.estatus = estatus;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public Long getId_etiqueta() {
        return id_etiqueta;
    }

    public void setId_etiqueta(Long id_etiqueta) {
        this.id_etiqueta = id_etiqueta;
    }

    public String getNombreEtiqueta() {
        return nombreEtiqueta;
    }

    public void setNombreEtiqueta(String nombreEtiqueta) {
        this.nombreEtiqueta = nombreEtiqueta;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public LocalDateTime getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(LocalDateTime fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public List<Products> getProductos() {
        return productos;
    }

    public void setProductos(List<Products> productos) {
        this.productos = productos;
    }

    @PrePersist
    protected void onCreate() {
        this.fecha_creacion = LocalDateTime.now();
        this.fecha_actualizacion = LocalDateTime.now(); // Al crear, ambas son iguales
        if (this.estatus == null) this.estatus = true;  // Default true si viene vacío
    }

    @PreUpdate
    protected void onUpdate() {
        this.fecha_actualizacion = LocalDateTime.now();
    }

    // --- UTILIDADES ---

    @Override
    public String toString() {
        return "Etiqueta{" +
                "id_etiqueta=" + id_etiqueta +
                ", nombreEtiqueta='" + nombreEtiqueta + '\'' +
                ", estatus=" + estatus +
                ", fecha_creacion=" + fecha_creacion +
                ", fecha_actualizacion=" + fecha_actualizacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Etiqueta etiqueta)) return false;
        return Objects.equals(id_etiqueta, etiqueta.id_etiqueta) &&
                Objects.equals(nombreEtiqueta, etiqueta.nombreEtiqueta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_etiqueta, nombreEtiqueta);
    }


}
