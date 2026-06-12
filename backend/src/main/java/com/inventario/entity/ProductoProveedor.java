package com.inventario.entity;

import jakarta.persistence.*;

// ========== ENTIDAD: PRODUCTO_PROVEEDOR ==========
// Relación muchos-a-muchos entre Producto y Proveedor
@Entity
@Table(name = "producto_proveedor")
public class ProductoProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_proveedor")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "costo", nullable = false)
    private Double costo;

    @Column(name = "tiempo_entrega")
    private Integer tiempoEntrega;

    @Column(name = "moneda")
    private String moneda;

    @Column(name = "activo")
    private Boolean activo;

    // Constructores
    public ProductoProveedor() {
        this.activo = true;
        this.moneda = "USD";
        this.tiempoEntrega = 0;
    }

    public ProductoProveedor(Producto producto, Proveedor proveedor, Double costo, Integer tiempoEntrega) {
        this.producto = producto;
        this.proveedor = proveedor;
        this.costo = costo;
        this.tiempoEntrega = tiempoEntrega;
        this.activo = true;
        this.moneda = "USD";
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Integer getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(Integer tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
