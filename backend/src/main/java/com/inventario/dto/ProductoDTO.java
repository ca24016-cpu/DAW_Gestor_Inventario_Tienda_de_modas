package com.inventario.dto;

import java.time.LocalDateTime;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long tallaId;
    private String tallaNombre;
    private String color;
    private String marca;
    private Double precioUnitario;
    private LocalDateTime fechaCreacion;
    private String estado;
    private String sku;

    public ProductoDTO() {}

    public ProductoDTO(Long id, String nombre, String descripcion, Long tallaId, String tallaNombre,
                      String color, String marca, Double precioUnitario, String estado, String sku) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tallaId = tallaId;
        this.tallaNombre = tallaNombre;
        this.color = color;
        this.marca = marca;
        this.precioUnitario = precioUnitario;
        this.estado = estado;
        this.sku = sku;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTallaId() {
        return tallaId;
    }

    public void setTallaId(Long tallaId) {
        this.tallaId = tallaId;
    }

    public String getTallaNombre() {
        return tallaNombre;
    }

    public void setTallaNombre(String tallaNombre) {
        this.tallaNombre = tallaNombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tallaId=" + tallaId +
                ", tallaNombre='" + tallaNombre + '\'' +
                ", color='" + color + '\'' +
                ", marca='" + marca + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", estado='" + estado + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }
}
