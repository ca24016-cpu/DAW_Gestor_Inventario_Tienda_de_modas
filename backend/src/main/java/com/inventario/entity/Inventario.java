package com.inventario.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// ========== ENTIDAD: INVENTARIO ==========
// Representa el inventario global de cada producto
@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_producto", nullable = false, unique = true)
    private Producto producto;

    @Column(name = "cantidad_actual", nullable = false)
    private Integer cantidadActual;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @Column(name = "stock_maximo")
    private Integer stockMaximo;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    // Constructores
    public Inventario() {
        this.cantidadActual = 0;
        this.stockMinimo = 0;
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public Inventario(Producto producto, Integer cantidadActual, Integer stockMinimo, Integer stockMaximo) {
        this.producto = producto;
        this.cantidadActual = cantidadActual;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.ultimaActualizacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Integer getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(Integer stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }
}
