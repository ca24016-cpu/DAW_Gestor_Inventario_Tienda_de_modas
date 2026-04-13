package com.inventario.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_producto")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_talla")
    private Talla talla;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    public Producto() {}
}
