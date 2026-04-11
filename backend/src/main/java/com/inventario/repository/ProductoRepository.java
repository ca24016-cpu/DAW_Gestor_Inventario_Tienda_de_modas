package com.inventario.repository;

import com.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    /**
     * Obtiene un producto por su SKU
     */
    Optional<Producto> findBySku(String sku);
    
    /**
     * Obtiene productos por marca
     */
    List<Producto> findByMarca(String marca);
    
    /**
     * Obtiene productos por talla
     */
    List<Producto> findByTallaId(Long tallaId);
    
    /**
     * Obtiene productos por estado
     */
    List<Producto> findByEstado(String estado);
    
    /**
     * Obtiene productos por rango de precio
     */
    List<Producto> findByPrecioUnitarioBetween(Double precioMin, Double precioMax);
}

