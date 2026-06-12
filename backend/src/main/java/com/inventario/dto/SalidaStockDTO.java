package com.inventario.dto;

import java.time.LocalDateTime;

// ========== DTO: SALIDA_STOCK ==========
// Data Transfer Object para la entidad SalidaStock
public class SalidaStockDTO {
    private Long id;
    private Long inventarioId;
    private String productoNombre;
    private Integer cantidad;
    private LocalDateTime fechaSalida;
    private String usuarioResponsable;
    private String motivo;
    private String tipoSalida;

    public SalidaStockDTO() {}

    public SalidaStockDTO(Long id, Long inventarioId, String productoNombre, Integer cantidad, 
                          String usuarioResponsable, String motivo, String tipoSalida) {
        this.id = id;
        this.inventarioId = inventarioId;
        this.productoNombre = productoNombre;
        this.cantidad = cantidad;
        this.usuarioResponsable = usuarioResponsable;
        this.motivo = motivo;
        this.tipoSalida = tipoSalida;
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

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(String tipoSalida) {
        this.tipoSalida = tipoSalida;
    }
}
