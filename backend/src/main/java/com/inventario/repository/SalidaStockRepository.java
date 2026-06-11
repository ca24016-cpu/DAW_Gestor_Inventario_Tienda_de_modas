package com.inventario.repository;

import com.inventario.entity.SalidaStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// ========== REPOSITORY: SALIDA_STOCK ==========
@Repository
public interface SalidaStockRepository extends JpaRepository<SalidaStock, Long> {
    
    // Obtener salidas por inventario
    List<SalidaStock> findByInventarioId(Long inventarioId);
    
    // Obtener salidas por tipo
    List<SalidaStock> findByTipoSalida(String tipoSalida);
    
    // Obtener salidas en rango de fechas
    List<SalidaStock> findByFechaSalidaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
