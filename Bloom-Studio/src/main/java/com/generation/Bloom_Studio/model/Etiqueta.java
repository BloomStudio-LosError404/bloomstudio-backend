package com.generation.Bloom_Studio.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "etiquetas")
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_etiqueta;

    @Column(nullable = false, length = 50)
    private String nombre_etiqueta;

    private Boolean estatus;

    @Column(updatable = false)
    private LocalDateTime fecha_creacion;

    private LocalDateTime fecha_actualizacion;

    // cocneccion con productos n:m





    public Etiqueta(Long id_etiqueta, String nombre_etiqueta, Boolean estatus, LocalDateTime fecha_creacion, LocalDateTime fecha_actualizacion) {
        this.id_etiqueta = id_etiqueta;
        this.nombre_etiqueta = nombre_etiqueta;
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

    public String getNombre_etiqueta() {
        return nombre_etiqueta;
    }

    public void setNombre_etiqueta(String nombre_etiqueta) {
        this.nombre_etiqueta = nombre_etiqueta;
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
                ", nombre_etiqueta='" + nombre_etiqueta + '\'' +
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
                Objects.equals(nombre_etiqueta, etiqueta.nombre_etiqueta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_etiqueta, nombre_etiqueta);
    }


}
