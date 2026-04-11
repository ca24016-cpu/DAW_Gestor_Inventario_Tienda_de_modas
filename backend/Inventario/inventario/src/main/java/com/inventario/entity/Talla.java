package com.inventario.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "talla")
public class Talla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_talla")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    public Talla() {}
}
