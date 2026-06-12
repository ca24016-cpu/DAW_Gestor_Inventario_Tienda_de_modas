package com.inventario.service;

import com.inventario.dto.ProductoCategoriaDTO;
import java.util.List;

// ========== SERVICE INTERFACE: PRODUCTO_CATEGORIA ==========
// Define operaciones de negocio para la gestión de relaciones Producto-Categoría
public interface ProductoCategoriaService {
    
    // Obtener todas las relaciones
    List<ProductoCategoriaDTO> obtenerTodas();
    
    // Obtener relación por ID
    ProductoCategoriaDTO obtenerPorId(Long id);
    
    // Obtener categorías de un producto
    List<ProductoCategoriaDTO> obtenerCategoriasPorProducto(Long productoId);
    
    // Obtener productos de una categoría
    List<ProductoCategoriaDTO> obtenerProductosPorCategoria(Long categoriaId);
    
    // Crear nueva relación
    ProductoCategoriaDTO crear(ProductoCategoriaDTO productoCategoriaDTO);
    
    // Eliminar relación
    void eliminar(Long id);
}
