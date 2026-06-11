package com.inventario.service.impl;

import com.inventario.dto.ProveedorDTO;
import com.inventario.entity.Proveedor;
import com.inventario.mapper.EntityDTOMapper;
import com.inventario.repository.ProveedorRepository;
import com.inventario.service.ProveedorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// ========== SERVICE IMPLEMENTATION: PROVEEDOR ==========
// Implementa la lógica de negocio para la gestión de Proveedores
@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {
    
    private final ProveedorRepository proveedorRepository;
    private final EntityDTOMapper mapper;
    
    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, EntityDTOMapper mapper) {
        this.proveedorRepository = proveedorRepository;
        this.mapper = mapper;
    }
    
    @Override
    public List<ProveedorDTO> obtenerTodos() {
        return proveedorRepository.findAll().stream()
                .map(mapper::proveedorToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProveedorDTO obtenerPorId(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        return mapper.proveedorToDTO(proveedor);
    }
    
    @Override
    public List<ProveedorDTO> obtenerActivos() {
        return proveedorRepository.findByActivoTrue().stream()
                .map(mapper::proveedorToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProveedorDTO crear(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = mapper.dtoToProveedor(proveedorDTO);
        Proveedor proveedorSaved = proveedorRepository.save(proveedor);
        return mapper.proveedorToDTO(proveedorSaved);
    }
    
    @Override
    public ProveedorDTO actualizar(Long id, ProveedorDTO proveedorDTO) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        
        if (proveedorDTO.getNombre() != null) {
            proveedor.setNombre(proveedorDTO.getNombre());
        }
        if (proveedorDTO.getContacto() != null) {
            proveedor.setContacto(proveedorDTO.getContacto());
        }
        if (proveedorDTO.getTelefono() != null) {
            proveedor.setTelefono(proveedorDTO.getTelefono());
        }
        if (proveedorDTO.getEmail() != null) {
            proveedor.setEmail(proveedorDTO.getEmail());
        }
        if (proveedorDTO.getDireccion() != null) {
            proveedor.setDireccion(proveedorDTO.getDireccion());
        }
        
        Proveedor proveedorUpdated = proveedorRepository.save(proveedor);
        return mapper.proveedorToDTO(proveedorUpdated);
    }
    
    @Override
    public void eliminar(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }
    
    @Override
    public ProveedorDTO cambiarEstado(Long id, Boolean activo) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        proveedor.setActivo(activo);
        Proveedor proveedorUpdated = proveedorRepository.save(proveedor);
        return mapper.proveedorToDTO(proveedorUpdated);
    }
}
