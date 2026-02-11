package com.generation.Bloom_Studio.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tallas")
public class Talla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_talla")
    private Long id;

    @Column(name = "nombre_talla", nullable = false, length = 10)
    private String nombreTalla;

    @Column(name = "estatus")
    private Boolean estatus;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private LocalDateTime fechaActualizacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTalla() {
        return nombreTalla;
    }

    public void setNombreTalla(String nombreTalla) {
        this.nombreTalla = nombreTalla;
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
        return "Tallas{" +
                "id=" + id +
                ", nombreTalla='" + nombreTalla + '\'' +
                ", estatus=" + estatus +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Talla tallas)) return false;
        return Objects.equals(id, tallas.id) && Objects.equals(nombreTalla, tallas.nombreTalla) && Objects.equals(estatus, tallas.estatus) && Objects.equals(fechaCreacion, tallas.fechaCreacion) && Objects.equals(fechaActualizacion, tallas.fechaActualizacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreTalla, estatus, fechaCreacion, fechaActualizacion);
    }
}
