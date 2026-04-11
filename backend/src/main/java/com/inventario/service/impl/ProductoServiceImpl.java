package com.inventario.service.impl;

import com.inventario.dto.ProductoDTO;
import com.inventario.entity.Producto;
import com.inventario.entity.Talla;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.ProductoRepository;
import com.inventario.repository.TallaRepository;
import com.inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TallaRepository tallaRepository;

    @Autowired
    private EntityDTOMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(mapper::productoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(mapper::productoToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> obtenerPorSku(String sku) {
        return productoRepository.findBySku(sku)
                .map(mapper::productoToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorMarca(String marca) {
        return productoRepository.findByMarca(marca)
                .stream()
                .map(mapper::productoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorTalla(Long tallaId) {
        return productoRepository.findByTallaId(tallaId)
                .stream()
                .map(mapper::productoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerProductosActivos() {
        return productoRepository.findByEstado("ACTIVO")
                .stream()
                .map(mapper::productoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO crear(ProductoDTO productoDTO) {
        // Validaciones
        if (productoDTO.getNombre() == null || productoDTO.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }
        if (productoDTO.getPrecioUnitario() == null || productoDTO.getPrecioUnitario() < 0) {
            throw new IllegalArgumentException("El precio unitario es requerido y debe ser positivo");
        }
        
        // Validar que el SKU sea único si se proporciona
        if (productoDTO.getSku() != null && !productoDTO.getSku().isEmpty()) {
            if (productoRepository.findBySku(productoDTO.getSku()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un producto con el SKU: " + productoDTO.getSku());
            }
        }

        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setColor(productoDTO.getColor());
        producto.setMarca(productoDTO.getMarca());
        producto.setPrecioUnitario(productoDTO.getPrecioUnitario());
        producto.setSku(productoDTO.getSku());
        producto.setEstado("ACTIVO");
        producto.setFechaCreacion(LocalDateTime.now());

        // Asignar talla si viene en el DTO
        if (productoDTO.getTallaId() != null) {
            Talla talla = tallaRepository.findById(productoDTO.getTallaId())
                    .orElseThrow(() -> new IllegalArgumentException("Talla no encontrada con ID: " + productoDTO.getTallaId()));
            producto.setTalla(talla);
        }

        Producto productoSaved = productoRepository.save(producto);
        return mapper.productoToDTO(productoSaved);
    }

    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        // Actualizar campos
        if (productoDTO.getNombre() != null) {
            producto.setNombre(productoDTO.getNombre());
        }
        if (productoDTO.getDescripcion() != null) {
            producto.setDescripcion(productoDTO.getDescripcion());
        }
        if (productoDTO.getColor() != null) {
            producto.setColor(productoDTO.getColor());
        }
        if (productoDTO.getMarca() != null) {
            producto.setMarca(productoDTO.getMarca());
        }
        if (productoDTO.getPrecioUnitario() != null) {
            if (productoDTO.getPrecioUnitario() < 0) {
                throw new IllegalArgumentException("El precio unitario no puede ser negativo");
            }
            producto.setPrecioUnitario(productoDTO.getPrecioUnitario());
        }

        // Actualizar talla si viene en el DTO
        if (productoDTO.getTallaId() != null) {
            Talla talla = tallaRepository.findById(productoDTO.getTallaId())
                    .orElseThrow(() -> new IllegalArgumentException("Talla no encontrada con ID: " + productoDTO.getTallaId()));
            producto.setTalla(talla);
        }

        // Actualizar SKU solo si es diferente y no existe otro producto con ese SKU
        if (productoDTO.getSku() != null && !productoDTO.getSku().equals(producto.getSku())) {
            if (productoRepository.findBySku(productoDTO.getSku()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un producto con el SKU: " + productoDTO.getSku());
            }
            producto.setSku(productoDTO.getSku());
        }

        Producto productoUpdated = productoRepository.save(producto);
        return mapper.productoToDTO(productoUpdated);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        productoRepository.delete(producto);
    }

    @Override
    public ProductoDTO cambiarEstado(Long id, String nuevoEstado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        // Validar que el estado sea válido
        List<String> estadosValidos = List.of("ACTIVO", "INACTIVO", "DESCONTINUADO");
        if (!estadosValidos.contains(nuevoEstado)) {
            throw new IllegalArgumentException("Estado inválido. Estados permitidos: " + estadosValidos);
        }

        producto.setEstado(nuevoEstado);
        Producto productoUpdated = productoRepository.save(producto);
        return mapper.productoToDTO(productoUpdated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorRangoprecio(Double precioMin, Double precioMax) {
        return productoRepository.findByPrecioUnitarioBetween(precioMin, precioMax)
                .stream()
                .map(mapper::productoToDTO)
                .collect(Collectors.toList());
    }
}
