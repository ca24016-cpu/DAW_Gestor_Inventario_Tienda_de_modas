package com.inventario.service.impl;

import com.inventario.dto.InventarioDTO;
import com.inventario.entity.Inventario;
import com.inventario.entity.Producto;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.InventarioRepository;
import com.inventario.repository.ProductoRepository;
import com.inventario.service.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// ========== SERVICE IMPLEMENTATION: INVENTARIO ==========
// Implementa la lógica de negocio para la gestión del Inventario
@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {
    
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final EntityDTOMapper mapper;
    
    public InventarioServiceImpl(InventarioRepository inventarioRepository, ProductoRepository productoRepository, EntityDTOMapper mapper) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
        this.mapper = mapper;
    }
    
    @Override
    public List<InventarioDTO> obtenerTodos() {
        return inventarioRepository.findAll().stream()
                .map(mapper::inventarioToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public InventarioDTO obtenerPorId(Long id) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
        return mapper.inventarioToDTO(inventario);
    }
    
    @Override
    public InventarioDTO obtenerPorProductoId(Long productoId) {
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para producto ID: " + productoId));
        return mapper.inventarioToDTO(inventario);
    }
    
    @Override
    public InventarioDTO crear(InventarioDTO inventarioDTO) {
        Producto producto = productoRepository.findById(inventarioDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + inventarioDTO.getProductoId()));
        
        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setCantidadActual(inventarioDTO.getCantidadActual());
        inventario.setStockMinimo(inventarioDTO.getStockMinimo());
        inventario.setStockMaximo(inventarioDTO.getStockMaximo());
        inventario.setUltimaActualizacion(LocalDateTime.now());
        
        Inventario inventarioSaved = inventarioRepository.save(inventario);
        return mapper.inventarioToDTO(inventarioSaved);
    }
    
    @Override
    public InventarioDTO actualizar(Long id, InventarioDTO inventarioDTO) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
        
        if (inventarioDTO.getStockMinimo() != null) {
            inventario.setStockMinimo(inventarioDTO.getStockMinimo());
        }
        if (inventarioDTO.getStockMaximo() != null) {
            inventario.setStockMaximo(inventarioDTO.getStockMaximo());
        }
        
        inventario.setUltimaActualizacion(LocalDateTime.now());
        Inventario inventarioUpdated = inventarioRepository.save(inventario);
        return mapper.inventarioToDTO(inventarioUpdated);
    }
    
    @Override
    public InventarioDTO actualizarCantidad(Long id, Integer nuevaCantidad) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
        
        inventario.setCantidadActual(nuevaCantidad);
        inventario.setUltimaActualizacion(LocalDateTime.now());
        Inventario inventarioUpdated = inventarioRepository.save(inventario);
        return mapper.inventarioToDTO(inventarioUpdated);
    }
    
    @Override
    public List<InventarioDTO> obtenerProductosStockBajo() {
        return inventarioRepository.findAll().stream()
                .filter(inventario -> inventario.getCantidadActual() <= inventario.getStockMinimo())
                .map(mapper::inventarioToDTO)
                .collect(Collectors.toList());
    }
}
