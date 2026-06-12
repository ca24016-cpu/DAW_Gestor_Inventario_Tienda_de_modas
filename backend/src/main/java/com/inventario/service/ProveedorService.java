package com.inventario.service;

import com.inventario.dto.ProveedorDTO;
import java.util.List;

// ========== SERVICE INTERFACE: PROVEEDOR ==========
// Define operaciones de negocio para la gestión de Proveedores
public interface ProveedorService {
    
    // Obtener todos los proveedores
    List<ProveedorDTO> obtenerTodos();
    
    // Obtener proveedor por ID
    ProveedorDTO obtenerPorId(Long id);
    
    // Obtener proveedores activos
    List<ProveedorDTO> obtenerActivos();
    
    // Crear nuevo proveedor
    ProveedorDTO crear(ProveedorDTO proveedorDTO);
    
    // Actualizar proveedor existente
    ProveedorDTO actualizar(Long id, ProveedorDTO proveedorDTO);
    
    // Eliminar proveedor
    void eliminar(Long id);
    
    // Cambiar estado de proveedor
    ProveedorDTO cambiarEstado(Long id, Boolean activo);
}
