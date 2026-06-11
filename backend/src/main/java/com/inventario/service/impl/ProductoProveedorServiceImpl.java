package com.inventario.service.impl;

import com.inventario.dto.ProductoProveedorDTO;
import com.inventario.entity.Producto;
import com.inventario.entity.Proveedor;
import com.inventario.entity.ProductoProveedor;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.ProductoProveedorRepository;
import com.inventario.repository.ProductoRepository;
import com.inventario.repository.ProveedorRepository;
import com.inventario.service.ProductoProveedorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ========== SERVICE IMPLEMENTATION: PRODUCTO_PROVEEDOR ==========
// Implementa la lógica de negocio para la gestión de relaciones Producto-Proveedor
@Service
@Transactional
public class ProductoProveedorServiceImpl implements ProductoProveedorService {
    
    private final ProductoProveedorRepository productoProveedorRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final EntityDTOMapper mapper;
    
    public ProductoProveedorServiceImpl(ProductoProveedorRepository productoProveedorRepository,
                                       ProductoRepository productoRepository,
                                       ProveedorRepository proveedorRepository,
                                       EntityDTOMapper mapper) {
        this.productoProveedorRepository = productoProveedorRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.mapper = mapper;
    }
    
    @Override
    public List<ProductoProveedorDTO> obtenerTodas() {
        return productoProveedorRepository.findAll().stream()
                .map(mapper::productoProveedorToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductoProveedorDTO obtenerPorId(Long id) {
        ProductoProveedor productoProveedor = productoProveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación Producto-Proveedor no encontrada con ID: " + id));
        return mapper.productoProveedorToDTO(productoProveedor);
    }
    
    @Override
    public List<ProductoProveedorDTO> obtenerProveedoresPorProducto(Long productoId) {
        return productoProveedorRepository.findByProductoId(productoId).stream()
                .map(mapper::productoProveedorToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductoProveedorDTO> obtenerProductosPorProveedor(Long proveedorId) {
        return productoProveedorRepository.findByProveedorId(proveedorId).stream()
                .map(mapper::productoProveedorToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductoProveedorDTO> obtenerProveedoresActivosPorProducto(Long productoId) {
        return productoProveedorRepository.findByProductoIdAndActivoTrue(productoId).stream()
                .map(mapper::productoProveedorToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductoProveedorDTO crear(ProductoProveedorDTO productoProveedorDTO) {
        Producto producto = productoRepository.findById(productoProveedorDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoProveedorDTO.getProductoId()));
        
        Proveedor proveedor = proveedorRepository.findById(productoProveedorDTO.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + productoProveedorDTO.getProveedorId()));
        
        ProductoProveedor productoProveedor = new ProductoProveedor();
        productoProveedor.setProducto(producto);
        productoProveedor.setProveedor(proveedor);
        productoProveedor.setCosto(productoProveedorDTO.getCosto());
        productoProveedor.setTiempoEntrega(productoProveedorDTO.getTiempoEntrega());
        productoProveedor.setMoneda(productoProveedorDTO.getMoneda());
        productoProveedor.setActivo(productoProveedorDTO.getActivo() != null ? productoProveedorDTO.getActivo() : true);
        
        ProductoProveedor productoProveedorCreated = productoProveedorRepository.save(productoProveedor);
        return mapper.productoProveedorToDTO(productoProveedorCreated);
    }
    
    @Override
    public ProductoProveedorDTO actualizar(Long id, ProductoProveedorDTO productoProveedorDTO) {
        ProductoProveedor productoProveedor = productoProveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación Producto-Proveedor no encontrada con ID: " + id));
        
        if (productoProveedorDTO.getCosto() != null) {
            productoProveedor.setCosto(productoProveedorDTO.getCosto());
        }
        if (productoProveedorDTO.getTiempoEntrega() != null) {
            productoProveedor.setTiempoEntrega(productoProveedorDTO.getTiempoEntrega());
        }
        if (productoProveedorDTO.getMoneda() != null) {
            productoProveedor.setMoneda(productoProveedorDTO.getMoneda());
        }
        
        ProductoProveedor productoProveedorUpdated = productoProveedorRepository.save(productoProveedor);
        return mapper.productoProveedorToDTO(productoProveedorUpdated);
    }
    
    @Override
    public void eliminar(Long id) {
        if (!productoProveedorRepository.existsById(id)) {
            throw new RuntimeException("Relación Producto-Proveedor no encontrada con ID: " + id);
        }
        productoProveedorRepository.deleteById(id);
    }
    
    @Override
    public ProductoProveedorDTO cambiarEstado(Long id, Boolean activo) {
        ProductoProveedor productoProveedor = productoProveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación Producto-Proveedor no encontrada con ID: " + id));
        
        productoProveedor.setActivo(activo);
        ProductoProveedor productoProveedorUpdated = productoProveedorRepository.save(productoProveedor);
        return mapper.productoProveedorToDTO(productoProveedorUpdated);
    }
}
