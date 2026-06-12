package com.inventario.dto;

import java.time.LocalDateTime;

// ========== DTO: PRODUCTO_CATEGORIA ==========
// Data Transfer Object para la relación Producto-Categoría
public class ProductoCategoriaDTO {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private Long categoriaId;
    private String categoriaNombre;
    private LocalDateTime fechaAsignacion;

    public ProductoCategoriaDTO() {}

    public ProductoCategoriaDTO(Long id, Long productoId, String productoNombre, Long categoriaId, String categoriaNombre) {
        this.id = id;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
