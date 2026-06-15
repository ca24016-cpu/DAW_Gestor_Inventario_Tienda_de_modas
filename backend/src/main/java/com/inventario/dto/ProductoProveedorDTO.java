package com.inventario.dto;

// ========== DTO: PRODUCTO_PROVEEDOR ==========
// Data Transfer Object para la relación Producto-Proveedor
public class ProductoProveedorDTO {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private Long proveedorId;
    private String proveedorNombre;
    private Double costo;
    private Integer tiempoEntrega;
    private String moneda;
    private Boolean activo;

    public ProductoProveedorDTO() {}

    public ProductoProveedorDTO(Long id, Long productoId, String productoNombre, Long proveedorId, 
                                String proveedorNombre, Double costo, Integer tiempoEntrega, String moneda) {
        this.id = id;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.proveedorId = proveedorId;
        this.proveedorNombre = proveedorNombre;
        this.costo = costo;
        this.tiempoEntrega = tiempoEntrega;
        this.moneda = moneda;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
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
