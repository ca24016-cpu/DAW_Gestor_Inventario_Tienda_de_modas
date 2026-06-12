package com.inventario.dto;

// ========== DTO: CATEGORIA ==========
// Data Transfer Object para la entidad Categoría
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer nivel;
    private Boolean activo;
    private Long categoriaPadreId;

    public CategoriaDTO() {}

    public CategoriaDTO(Long id, String nombre, String descripcion, Integer nivel, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.activo = activo;
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getCategoriaPadreId() {
        return categoriaPadreId;
    }

    public void setCategoriaPadreId(Long categoriaPadreId) {
        this.categoriaPadreId = categoriaPadreId;
    }
}
