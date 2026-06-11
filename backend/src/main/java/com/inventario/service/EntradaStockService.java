package com.inventario.service;

import com.inventario.dto.EntradaStockDTO;
import java.util.List;

// ========== SERVICE INTERFACE: ENTRADA_STOCK ==========
// Define operaciones de negocio para la gestión de Entradas de Stock
public interface EntradaStockService {
    
    // Obtener todas las entradas
    List<EntradaStockDTO> obtenerTodas();
    
    // Obtener entrada por ID
    EntradaStockDTO obtenerPorId(Long id);
    
    // Obtener entradas por inventario
    List<EntradaStockDTO> obtenerPorInventario(Long inventarioId);
    
    // Crear nueva entrada de stock
    EntradaStockDTO crear(EntradaStockDTO entradaStockDTO);
    
    // Obtener entradas por tipo
    List<EntradaStockDTO> obtenerPorTipo(String tipoEntrada);
}
