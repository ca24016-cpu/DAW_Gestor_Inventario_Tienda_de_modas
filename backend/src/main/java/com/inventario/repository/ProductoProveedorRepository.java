package com.inventario.repository;

import com.inventario.entity.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ========== REPOSITORY: PRODUCTO_PROVEEDOR ==========
@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Long> {
    
    // Obtener proveedores de un producto
    List<ProductoProveedor> findByProductoId(Long productoId);
    
    // Obtener productos de un proveedor
    List<ProductoProveedor> findByProveedorId(Long proveedorId);
    
    // Obtener relación activa entre producto y proveedor
    List<ProductoProveedor> findByProductoIdAndActivoTrue(Long productoId);
}
