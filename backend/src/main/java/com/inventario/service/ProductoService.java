package com.inventario.service;

import com.inventario.dto.ProductoDTO;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    
    /**
     * Obtiene todos los productos
     */
    List<ProductoDTO> obtenerTodos();
    
    /**
     * Obtiene un producto por su ID
     */
    Optional<ProductoDTO> obtenerPorId(Long id);
    
    /**
     * Obtiene un producto por su SKU
     */
    Optional<ProductoDTO> obtenerPorSku(String sku);
    
    /**
     * Obtiene productos por marca
     */
    List<ProductoDTO> obtenerPorMarca(String marca);
    
    /**
     * Obtiene productos por talla
     */
    List<ProductoDTO> obtenerPorTalla(Long tallaId);
    
    /**
     * Obtiene productos activos
     */
    List<ProductoDTO> obtenerProductosActivos();
    
    /**
     * Crea un nuevo producto
     */
    ProductoDTO crear(ProductoDTO productoDTO);
    
    /**
     * Actualiza un producto existente
     */
    ProductoDTO actualizar(Long id, ProductoDTO productoDTO);
    
    /**
     * Elimina un producto
     */
    void eliminar(Long id);
    
    /**
     * Cambiar el estado de un producto
     */
    ProductoDTO cambiarEstado(Long id, String nuevoEstado);
    
    /**
     * Obtiene productos por rango de precio
     */
    List<ProductoDTO> obtenerPorRangoprecio(Double precioMin, Double precioMax);
}
