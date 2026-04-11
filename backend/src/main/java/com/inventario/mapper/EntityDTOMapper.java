package com.inventario.mapper;

import com.inventario.dto.TallaDTO;
import com.inventario.dto.ProductoDTO;
import com.inventario.entity.Talla;
import com.inventario.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class EntityDTOMapper {

    // ===== TALLA MAPPINGS =====
    public TallaDTO tallaToDTO(Talla talla) {
        if (talla == null) {
            return null;
        }
        return new TallaDTO(
                talla.getId(),
                talla.getNombre(),
                talla.getCategoria(),
                talla.getOrden()
        );
    }

    public Talla dtoToTalla(TallaDTO tallaDTO) {
        if (tallaDTO == null) {
            return null;
        }
        Talla talla = new Talla();
        talla.setId(tallaDTO.getId());
        talla.setNombre(tallaDTO.getNombre());
        talla.setCategoria(tallaDTO.getCategoria());
        talla.setOrden(tallaDTO.getOrden());
        return talla;
    }

    // ===== PRODUCTO MAPPINGS =====
    public ProductoDTO productoToDTO(Producto producto) {
        if (producto == null) {
            return null;
        }
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setColor(producto.getColor());
        dto.setMarca(producto.getMarca());
        dto.setPrecioUnitario(producto.getPrecioUnitario());
        dto.setFechaCreacion(producto.getFechaCreacion());
        dto.setEstado(producto.getEstado());
        dto.setSku(producto.getSku());
        
        if (producto.getTalla() != null) {
            dto.setTallaId(producto.getTalla().getId());
            dto.setTallaNombre(producto.getTalla().getNombre());
        }
        
        return dto;
    }

    public Producto dtoToProducto(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setColor(productoDTO.getColor());
        producto.setMarca(productoDTO.getMarca());
        producto.setPrecioUnitario(productoDTO.getPrecioUnitario());
        producto.setFechaCreacion(productoDTO.getFechaCreacion());
        producto.setEstado(productoDTO.getEstado());
        producto.setSku(productoDTO.getSku());
        
        return producto;
    }
}
