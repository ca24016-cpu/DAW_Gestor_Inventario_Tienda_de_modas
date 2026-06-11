package com.inventario.service.impl;

import com.inventario.dto.SalidaStockDTO;
import com.inventario.entity.Inventario;
import com.inventario.entity.SalidaStock;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.InventarioRepository;
import com.inventario.repository.SalidaStockRepository;
import com.inventario.service.SalidaStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ========== SERVICE IMPLEMENTATION: SALIDA_STOCK ==========
// Implementa la lógica de negocio para la gestión de Salidas de Stock
@Service
@Transactional
public class SalidaStockServiceImpl implements SalidaStockService {
    
    private final SalidaStockRepository salidaStockRepository;
    private final InventarioRepository inventarioRepository;
    private final EntityDTOMapper mapper;
    
    public SalidaStockServiceImpl(SalidaStockRepository salidaStockRepository, InventarioRepository inventarioRepository, EntityDTOMapper mapper) {
        this.salidaStockRepository = salidaStockRepository;
        this.inventarioRepository = inventarioRepository;
        this.mapper = mapper;
    }
    
    @Override
    public List<SalidaStockDTO> obtenerTodas() {
        return salidaStockRepository.findAll().stream()
                .map(mapper::salidaStockToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public SalidaStockDTO obtenerPorId(Long id) {
        SalidaStock salidaStock = salidaStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salida de stock no encontrada con ID: " + id));
        return mapper.salidaStockToDTO(salidaStock);
    }
    
    @Override
    public List<SalidaStockDTO> obtenerPorInventario(Long inventarioId) {
        return salidaStockRepository.findByInventarioId(inventarioId).stream()
                .map(mapper::salidaStockToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public SalidaStockDTO crear(SalidaStockDTO salidaStockDTO) {
        Inventario inventario = inventarioRepository.findById(salidaStockDTO.getInventarioId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + salidaStockDTO.getInventarioId()));
        
        // Verificar que hay suficiente stock
        if (inventario.getCantidadActual() < salidaStockDTO.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para la salida. Stock disponible: " + inventario.getCantidadActual());
        }
        
        SalidaStock salidaStock = mapper.dtoToSalidaStock(salidaStockDTO);
        salidaStock.setInventario(inventario);
        
        // Actualizar stock del inventario
        inventario.setCantidadActual(inventario.getCantidadActual() - salidaStock.getCantidad());
        inventarioRepository.save(inventario);
        
        SalidaStock salidaStockSaved = salidaStockRepository.save(salidaStock);
        return mapper.salidaStockToDTO(salidaStockSaved);
    }
    
    @Override
    public List<SalidaStockDTO> obtenerPorTipo(String tipoSalida) {
        return salidaStockRepository.findByTipoSalida(tipoSalida).stream()
                .map(mapper::salidaStockToDTO)
                .collect(Collectors.toList());
    }
}
