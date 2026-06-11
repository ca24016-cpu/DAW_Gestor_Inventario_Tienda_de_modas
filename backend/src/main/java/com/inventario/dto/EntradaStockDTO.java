package com.inventario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

// ========== DTO: ENTRADA_STOCK ==========
// Data Transfer Object para la entidad EntradaStock
public class EntradaStockDTO {
    private Long id;
    private Long inventarioId;
    private String productoNombre;
    private Integer cantidad;
    private LocalDateTime fechaEntrada;
    private String usuarioResponsable;
    private String tipoEntrada;
    private String lote;
    private LocalDate fechaVencimiento;

    public EntradaStockDTO() {}

    public EntradaStockDTO(Long id, Long inventarioId, String productoNombre, Integer cantidad, 
                           String usuarioResponsable, String tipoEntrada) {
        this.id = id;
        this.inventarioId = inventarioId;
        this.productoNombre = productoNombre;
        this.cantidad = cantidad;
        this.usuarioResponsable = usuarioResponsable;
        this.tipoEntrada = tipoEntrada;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Long inventarioId) {
        this.inventarioId = inventarioId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
