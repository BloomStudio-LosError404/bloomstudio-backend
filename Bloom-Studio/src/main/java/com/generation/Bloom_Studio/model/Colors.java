package com.generation.Bloom_Studio.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "colores")
public class Colors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_color;

    @Column(nullable = false, length = 50)
    private String nombre_color;

    @Column(nullable = false, length = 7)
    private String codigo_hex;

    private Boolean estatus;

    @Column(updatable = false)
    private LocalDateTime fecha_creacion;

    private LocalDateTime fecha_actualizacion;

    // conección con productos 1:1



    public Colors(Long id_color, String nombre_color, String codigo_hex, Boolean estatus, LocalDateTime fecha_creacion, LocalDateTime fecha_actualizacion) {
        this.id_color = id_color;
        this.nombre_color = nombre_color;
        this.codigo_hex = codigo_hex;
        this.estatus = estatus;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }


    public Long getId_color() {
        return id_color;
    }

    public void setId_color(Long id_color) {
        this.id_color = id_color;
    }

    public String getNombre_color() {
        return nombre_color;
    }

    public void setNombre_color(String nombre_color) {
        this.nombre_color = nombre_color;
    }

    public String getCodigo_hex() {
        return codigo_hex;
    }

    public void setCodigo_hex(String codigo_hex) {
        this.codigo_hex = codigo_hex;
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
        return "Colors{" +
                "id_color=" + id_color +
                ", nombre_color='" + nombre_color + '\'' +
                ", codigo_hex='" + codigo_hex + '\'' +
                ", estatus=" + estatus +
                ", fecha_creacion=" + fecha_creacion +
                ", fecha_actualizacion=" + fecha_actualizacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Colors colors)) return false;
        return Objects.equals(id_color, colors.id_color) && Objects.equals(nombre_color, colors.nombre_color) && Objects.equals(codigo_hex, colors.codigo_hex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_color, nombre_color, codigo_hex);
    }
}
