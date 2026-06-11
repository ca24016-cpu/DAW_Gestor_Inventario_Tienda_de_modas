package com.inventario.service;

import com.inventario.dto.ProductoProveedorDTO;
import java.util.List;

// ========== SERVICE INTERFACE: PRODUCTO_PROVEEDOR ==========
// Define operaciones de negocio para la gestión de relaciones Producto-Proveedor
public interface ProductoProveedorService {
    
    // Obtener todas las relaciones
    List<ProductoProveedorDTO> obtenerTodas();
    
    // Obtener relación por ID
    ProductoProveedorDTO obtenerPorId(Long id);
    
    // Obtener proveedores de un producto
    List<ProductoProveedorDTO> obtenerProveedoresPorProducto(Long productoId);
    
    // Obtener productos de un proveedor
    List<ProductoProveedorDTO> obtenerProductosPorProveedor(Long proveedorId);
    
    // Obtener proveedores activos de un producto
    List<ProductoProveedorDTO> obtenerProveedoresActivosPorProducto(Long productoId);
    
    // Crear nueva relación
    ProductoProveedorDTO crear(ProductoProveedorDTO productoProveedorDTO);
    
    // Actualizar relación
    ProductoProveedorDTO actualizar(Long id, ProductoProveedorDTO productoProveedorDTO);
    
    // Eliminar relación
    void eliminar(Long id);
    
    // Cambiar estado
    ProductoProveedorDTO cambiarEstado(Long id, Boolean activo);
}
