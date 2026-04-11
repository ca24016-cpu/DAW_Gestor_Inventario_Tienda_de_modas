package com.inventario.service;

import com.inventario.dto.TallaDTO;
import java.util.List;
import java.util.Optional;

public interface TallaService {
    
    /**
     * Obtiene todas las tallas
     */
    List<TallaDTO> obtenerTodas();
    
    /**
     * Obtiene una talla por su ID
     */
    Optional<TallaDTO> obtenerPorId(Long id);
    
    /**
     * Obtiene tallas por categoría
     */
    List<TallaDTO> obtenerPorCategoria(String categoria);
    
    /**
     * Crea una nueva talla
     */
    TallaDTO crear(TallaDTO tallaDTO);
    
    /**
     * Actualiza una talla existente
     */
    TallaDTO actualizar(Long id, TallaDTO tallaDTO);
    
    /**
     * Elimina una talla
     */
    void eliminar(Long id);
    
    /**
     * Obtiene las tallas ordenadas por orden
     */
    List<TallaDTO> obtenerOrdenadas();
}
