package com.inventario.service;

import com.inventario.dto.CategoriaDTO;
import java.util.List;

// ========== SERVICE INTERFACE: CATEGORIA ==========
// Define operaciones de negocio para la gestión de Categorías
public interface CategoriaService {
    
    // Obtener todas las categorías
    List<CategoriaDTO> obtenerTodas();
    
    // Obtener categoría por ID
    CategoriaDTO obtenerPorId(Long id);
    
    // Obtener categorías activas
    List<CategoriaDTO> obtenerActivas();
    
    // Crear nueva categoría
    CategoriaDTO crear(CategoriaDTO categoriaDTO);
    
    // Actualizar categoría existente
    CategoriaDTO actualizar(Long id, CategoriaDTO categoriaDTO);
    
    // Eliminar categoría
    void eliminar(Long id);
    
    // Obtener subcategorías
    List<CategoriaDTO> obtenerSubcategorias(Long categoriaPadreId);
}
