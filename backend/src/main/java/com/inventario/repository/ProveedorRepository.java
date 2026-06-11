package com.inventario.repository;

import com.inventario.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ========== REPOSITORY: PROVEEDOR ==========
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    
    // Obtener proveedores activos
    List<Proveedor> findByActivoTrue();
    
    // Buscar proveedor por nombre
    Proveedor findByNombre(String nombre);
    
    // Buscar proveedores por email
    Proveedor findByEmail(String email);
}
