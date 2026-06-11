package com.inventario.service;

import com.inventario.dto.InventarioDTO;
import java.util.List;

// ========== SERVICE INTERFACE: INVENTARIO ==========
// Define operaciones de negocio para la gestión del Inventario
public interface InventarioService {
    
    // Obtener todos los inventarios
    List<InventarioDTO> obtenerTodos();
    
    // Obtener inventario por ID
    InventarioDTO obtenerPorId(Long id);
    
    // Obtener inventario por ID de producto
    InventarioDTO obtenerPorProductoId(Long productoId);
    
    // Crear nuevo inventario
    InventarioDTO crear(InventarioDTO inventarioDTO);
    
    // Actualizar inventario
    InventarioDTO actualizar(Long id, InventarioDTO inventarioDTO);
    
    // Actualizar cantidad de stock
    InventarioDTO actualizarCantidad(Long id, Integer nuevaCantidad);
    
    // Obtener productos con stock bajo
    List<InventarioDTO> obtenerProductosStockBajo();
}
