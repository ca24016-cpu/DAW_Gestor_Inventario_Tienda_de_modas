package com.inventario.service.impl;

import com.inventario.dto.CategoriaDTO;
import com.inventario.entity.Categoria;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.CategoriaRepository;
import com.inventario.service.CategoriaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ========== SERVICE IMPLEMENTATION: CATEGORIA ==========
// Implementa la lógica de negocio para la gestión de Categorías
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    private final EntityDTOMapper mapper;
    
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, EntityDTOMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public List<CategoriaDTO> obtenerTodas() {
        return categoriaRepository.findAll().stream()
                .map(mapper::categoriaToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CategoriaDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return mapper.categoriaToDTO(categoria);
    }
    
    @Override
    public List<CategoriaDTO> obtenerActivas() {
        return categoriaRepository.findByActivoTrue().stream()
                .map(mapper::categoriaToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CategoriaDTO crear(CategoriaDTO categoriaDTO) {
        Categoria categoria = mapper.dtoToCategoria(categoriaDTO);
        Categoria categoriaSaved = categoriaRepository.save(categoria);
        return mapper.categoriaToDTO(categoriaSaved);
    }
    
    @Override
    public CategoriaDTO actualizar(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        if (categoriaDTO.getNombre() != null) {
            categoria.setNombre(categoriaDTO.getNombre());
        }
        if (categoriaDTO.getDescripcion() != null) {
            categoria.setDescripcion(categoriaDTO.getDescripcion());
        }
        if (categoriaDTO.getNivel() != null) {
            categoria.setNivel(categoriaDTO.getNivel());
        }
        if (categoriaDTO.getActivo() != null) {
            categoria.setActivo(categoriaDTO.getActivo());
        }
        
        Categoria categoriaUpdated = categoriaRepository.save(categoria);
        return mapper.categoriaToDTO(categoriaUpdated);
    }
    
    @Override
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
    
    @Override
    public List<CategoriaDTO> obtenerSubcategorias(Long categoriaPadreId) {
        return categoriaRepository.findByCategoriaPadreId(categoriaPadreId).stream()
                .map(mapper::categoriaToDTO)
                .collect(Collectors.toList());
    }
}
