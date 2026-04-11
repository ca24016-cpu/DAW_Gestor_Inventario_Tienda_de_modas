package com.inventario.service.impl;

import com.inventario.dto.TallaDTO;
import com.inventario.entity.Talla;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.TallaRepository;
import com.inventario.service.TallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TallaServiceImpl implements TallaService {

    @Autowired
    private TallaRepository tallaRepository;

    @Autowired
    private EntityDTOMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<TallaDTO> obtenerTodas() {
        return tallaRepository.findAll()
                .stream()
                .map(mapper::tallaToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TallaDTO> obtenerPorId(Long id) {
        return tallaRepository.findById(id)
                .map(mapper::tallaToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TallaDTO> obtenerPorCategoria(String categoria) {
        return tallaRepository.findByCategoria(categoria)
                .stream()
                .map(mapper::tallaToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TallaDTO crear(TallaDTO tallaDTO) {
        if (tallaDTO.getNombre() == null || tallaDTO.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la talla es requerido");
        }
        
        Talla talla = new Talla(
                tallaDTO.getNombre(),
                tallaDTO.getCategoria(),
                tallaDTO.getOrden()
        );
        
        Talla tallaSaved = tallaRepository.save(talla);
        return mapper.tallaToDTO(tallaSaved);
    }

    @Override
    public TallaDTO actualizar(Long id, TallaDTO tallaDTO) {
        Talla talla = tallaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Talla no encontrada con ID: " + id));
        
        if (tallaDTO.getNombre() != null) {
            talla.setNombre(tallaDTO.getNombre());
        }
        if (tallaDTO.getCategoria() != null) {
            talla.setCategoria(tallaDTO.getCategoria());
        }
        if (tallaDTO.getOrden() != null) {
            talla.setOrden(tallaDTO.getOrden());
        }
        
        Talla tallaUpdated = tallaRepository.save(talla);
        return mapper.tallaToDTO(tallaUpdated);
    }

    @Override
    public void eliminar(Long id) {
        Talla talla = tallaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Talla no encontrada con ID: " + id));
        tallaRepository.delete(talla);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TallaDTO> obtenerOrdenadas() {
        return tallaRepository.findByOrderByOrden()
                .stream()
                .map(mapper::tallaToDTO)
                .collect(Collectors.toList());
    }
}
