package com.inventario.dto;

public class TallaDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private Integer orden;

    public TallaDTO() {}

    public TallaDTO(Long id, String nombre, String categoria, Integer orden) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.orden = orden;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "TallaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", orden=" + orden +
                '}';
    }
}
