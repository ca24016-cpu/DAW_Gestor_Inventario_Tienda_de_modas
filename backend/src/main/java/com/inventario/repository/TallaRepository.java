package com.inventario.repository;

import com.inventario.entity.Talla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TallaRepository extends JpaRepository<Talla, Long> {
    
    /**
     * Obtiene tallas por categoría
     */
    List<Talla> findByCategoria(String categoria);
    
    /**
     * Obtiene tallas ordenadas por el campo orden
     */
    List<Talla> findByOrderByOrden();
}
