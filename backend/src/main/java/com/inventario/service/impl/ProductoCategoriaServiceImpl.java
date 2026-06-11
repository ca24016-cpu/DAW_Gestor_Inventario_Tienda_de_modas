package com.inventario.service.impl;

import com.inventario.dto.ProductoCategoriaDTO;
import com.inventario.entity.Categoria;
import com.inventario.entity.Producto;
import com.inventario.entity.ProductoCategoria;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.CategoriaRepository;
import com.inventario.repository.ProductoCategoriaRepository;
import com.inventario.repository.ProductoRepository;
import com.inventario.service.ProductoCategoriaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ========== SERVICE IMPLEMENTATION: PRODUCTO_CATEGORIA ==========
// Implementa la lógica de negocio para la gestión de relaciones Producto-Categoría
@Service
@Transactional
public class ProductoCategoriaServiceImpl implements ProductoCategoriaService {
    
    private final ProductoCategoriaRepository productoCategoriaRepository;
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final EntityDTOMapper mapper;
    
    public ProductoCategoriaServiceImpl(ProductoCategoriaRepository productoCategoriaRepository, 
                                      ProductoRepository productoRepository,
                                      CategoriaRepository categoriaRepository,
                                      EntityDTOMapper mapper) {
        this.productoCategoriaRepository = productoCategoriaRepository;
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public List<ProductoCategoriaDTO> obtenerTodas() {
        return productoCategoriaRepository.findAll().stream()
                .map(mapper::productoCategoriaToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductoCategoriaDTO obtenerPorId(Long id) {
        ProductoCategoria productoCategoria = productoCategoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación Producto-Categoría no encontrada con ID: " + id));
        return mapper.productoCategoriaToDTO(productoCategoria);
    }
    
    @Override
    public List<ProductoCategoriaDTO> obtenerCategoriasPorProducto(Long productoId) {
        return productoCategoriaRepository.findByProductoId(productoId).stream()
                .map(mapper::productoCategoriaToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductoCategoriaDTO> obtenerProductosPorCategoria(Long categoriaId) {
        return productoCategoriaRepository.findByCategoriaId(categoriaId).stream()
                .map(mapper::productoCategoriaToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductoCategoriaDTO crear(ProductoCategoriaDTO productoCategoriaDTO) {
        Producto producto = productoRepository.findById(productoCategoriaDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoCategoriaDTO.getProductoId()));
        
        Categoria categoria = categoriaRepository.findById(productoCategoriaDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + productoCategoriaDTO.getCategoriaId()));
        
        ProductoCategoria productoCategoria = new ProductoCategoria();
        productoCategoria.setProducto(producto);
        productoCategoria.setCategoria(categoria);
        
        ProductoCategoria productoCategoriaCreated = productoCategoriaRepository.save(productoCategoria);
        return mapper.productoCategoriaToDTO(productoCategoriaCreated);
    }
    
    @Override
    public void eliminar(Long id) {
        if (!productoCategoriaRepository.existsById(id)) {
            throw new RuntimeException("Relación Producto-Categoría no encontrada con ID: " + id);
        }
        productoCategoriaRepository.deleteById(id);
    }
}
