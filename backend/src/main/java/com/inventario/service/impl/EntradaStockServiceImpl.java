package com.inventario.service.impl;

import com.inventario.dto.EntradaStockDTO;
import com.inventario.entity.EntradaStock;
import com.inventario.entity.Inventario;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.EntradaStockRepository;
import com.inventario.repository.InventarioRepository;
import com.inventario.service.EntradaStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ========== SERVICE IMPLEMENTATION: ENTRADA_STOCK ==========
// Implementa la lógica de negocio para la gestión de Entradas de Stock
@Service
@Transactional
public class EntradaStockServiceImpl implements EntradaStockService {
    
    private final EntradaStockRepository entradaStockRepository;
    private final InventarioRepository inventarioRepository;
    private final EntityDTOMapper mapper;
    
    public EntradaStockServiceImpl(EntradaStockRepository entradaStockRepository, InventarioRepository inventarioRepository, EntityDTOMapper mapper) {
        this.entradaStockRepository = entradaStockRepository;
        this.inventarioRepository = inventarioRepository;
        this.mapper = mapper;
    }
    
    @Override
    public List<EntradaStockDTO> obtenerTodas() {
        return entradaStockRepository.findAll().stream()
                .map(mapper::entradaStockToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public EntradaStockDTO obtenerPorId(Long id) {
        EntradaStock entradaStock = entradaStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada de stock no encontrada con ID: " + id));
        return mapper.entradaStockToDTO(entradaStock);
    }
    
    @Override
    public List<EntradaStockDTO> obtenerPorInventario(Long inventarioId) {
        return entradaStockRepository.findByInventarioId(inventarioId).stream()
                .map(mapper::entradaStockToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public EntradaStockDTO crear(EntradaStockDTO entradaStockDTO) {
        Inventario inventario = inventarioRepository.findById(entradaStockDTO.getInventarioId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + entradaStockDTO.getInventarioId()));
        
        EntradaStock entradaStock = mapper.dtoToEntradaStock(entradaStockDTO);
        entradaStock.setInventario(inventario);
        
        // Actualizar stock del inventario
        inventario.setCantidadActual(inventario.getCantidadActual() + entradaStock.getCantidad());
        inventarioRepository.save(inventario);
        
        EntradaStock entradaStockSaved = entradaStockRepository.save(entradaStock);
        return mapper.entradaStockToDTO(entradaStockSaved);
    }
    
    @Override
    public List<EntradaStockDTO> obtenerPorTipo(String tipoEntrada) {
        return entradaStockRepository.findByTipoEntrada(tipoEntrada).stream()
                .map(mapper::entradaStockToDTO)
                .collect(Collectors.toList());
    }
}
