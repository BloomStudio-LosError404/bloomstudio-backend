package com.generation.Bloom_Studio.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tallas")
public class Tallas {

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


}
