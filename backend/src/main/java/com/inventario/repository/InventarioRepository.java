package com.inventario.repository;

import com.inventario.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// ========== REPOSITORY: INVENTARIO ==========
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    
    // Obtener inventario por ID de producto
    Optional<Inventario> findByProductoId(Long productoId);
}
