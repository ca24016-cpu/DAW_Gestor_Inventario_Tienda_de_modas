package com.inventario.service;

import com.inventario.dto.SalidaStockDTO;
import java.util.List;

// ========== SERVICE INTERFACE: SALIDA_STOCK ==========
// Define operaciones de negocio para la gestión de Salidas de Stock
public interface SalidaStockService {
    
    // Obtener todas las salidas
    List<SalidaStockDTO> obtenerTodas();
    
    // Obtener salida por ID
    SalidaStockDTO obtenerPorId(Long id);
    
    // Obtener salidas por inventario
    List<SalidaStockDTO> obtenerPorInventario(Long inventarioId);
    
    // Crear nueva salida de stock
    SalidaStockDTO crear(SalidaStockDTO salidaStockDTO);
    
    // Obtener salidas por tipo
    List<SalidaStockDTO> obtenerPorTipo(String tipoSalida);
}
