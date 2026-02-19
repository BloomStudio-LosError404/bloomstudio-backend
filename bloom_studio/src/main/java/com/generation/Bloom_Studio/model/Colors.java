package com.generation.Bloom_Studio.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "colores")
public class Colors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_color")
    private Long id;

    @Column(name = "nombre_color", nullable = false, length = 50)
    private String nombreColor;

    @Column(name = "codigo_hex", nullable = false, length = 7)
    private String codigoHex;

    private Boolean estatus;

    @Column(updatable = false)
    private LocalDateTime fecha_creacion;

    private LocalDateTime fecha_actualizacion;

    // Colors.java
    @OneToMany(mappedBy = "color")
    private List<Inventory> inventories;

    public Long getIdColor() { return this.id; }


    // conección con productos 1:1



    public Colors(Long id, String nombreColor, String codigoHex, Boolean estatus, LocalDateTime fecha_creacion, LocalDateTime fecha_actualizacion) {
        this.id = id;
        this.nombreColor = nombreColor;
        this.codigoHex = codigoHex;
        this.estatus = estatus;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public Colors() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }

    public String getCodigoHex() {
        return codigoHex;
    }

    public void setCodigoHex(String codigoHex) {
        this.codigoHex = codigoHex;
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
                "id=" + id +
                ", nombreColor'" + nombreColor + '\'' +
                ", codigoHex='" + codigoHex + '\'' +
                ", estatus=" + estatus +
                ", fecha_creacion=" + fecha_creacion +
                ", fecha_actualizacion=" + fecha_actualizacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Colors colors)) return false;
        return Objects.equals(id, colors.id) && Objects.equals(nombreColor, colors.nombreColor) && Objects.equals(codigoHex, colors.codigoHex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreColor, codigoHex);
    }


}

