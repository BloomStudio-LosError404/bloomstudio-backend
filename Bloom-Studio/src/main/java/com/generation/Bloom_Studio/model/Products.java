package com.generation.Bloom_Studio.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="productos")
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

    @Column(name = "precio")
    private BigDecimal precio;

}
