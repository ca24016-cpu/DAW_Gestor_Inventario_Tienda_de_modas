package com.inventario.repository;

import com.inventario.entity.EntradaStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// ========== REPOSITORY: ENTRADA_STOCK ==========
@Repository
public interface EntradaStockRepository extends JpaRepository<EntradaStock, Long> {
    
    // Obtener entradas por inventario
    List<EntradaStock> findByInventarioId(Long inventarioId);
    
    // Obtener entradas por tipo
    List<EntradaStock> findByTipoEntrada(String tipoEntrada);
    
    // Obtener entradas en rango de fechas
    List<EntradaStock> findByFechaEntradaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
