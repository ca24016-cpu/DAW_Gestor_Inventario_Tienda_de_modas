package com.inventario.repository;

import com.inventario.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ========== REPOSITORY: CATEGORIA ==========
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    // Obtener categorías activas
    List<Categoria> findByActivoTrue();
    
    // Obtener categorías por nivel
    List<Categoria> findByNivel(Integer nivel);
    
    // Obtener subcategorías de una categoría padre
    List<Categoria> findByCategoriaPadreId(Long categoriaPadreId);
    
    // Buscar categoría por nombre
    Categoria findByNombre(String nombre);
}
