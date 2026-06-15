package com.inventario.repository;

import com.inventario.entity.ProductoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ========== REPOSITORY: PRODUCTO_CATEGORIA ==========
@Repository
public interface ProductoCategoriaRepository extends JpaRepository<ProductoCategoria, Long> {
    
    // Obtener categorías de un producto
    List<ProductoCategoria> findByProductoId(Long productoId);
    
    // Obtener productos de una categoría
    List<ProductoCategoria> findByCategoriaId(Long categoriaId);
}
