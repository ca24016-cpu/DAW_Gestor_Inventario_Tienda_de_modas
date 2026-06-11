package com.inventario.mapper;

import com.inventario.dto.*;
import com.inventario.entity.*;
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

    // ===== CATEGORIA MAPPINGS =====
    public CategoriaDTO categoriaToDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setNivel(categoria.getNivel());
        dto.setActivo(categoria.getActivo());
        
        if (categoria.getCategoriaPadre() != null) {
            dto.setCategoriaPadreId(categoria.getCategoriaPadre().getId());
        }
        
        return dto;
    }

    public Categoria dtoToCategoria(CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setNivel(categoriaDTO.getNivel());
        categoria.setActivo(categoriaDTO.getActivo());
        
        return categoria;
    }

    // ===== PROVEEDOR MAPPINGS =====
    public ProveedorDTO proveedorToDTO(Proveedor proveedor) {
        if (proveedor == null) {
            return null;
        }
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setContacto(proveedor.getContacto());
        dto.setTelefono(proveedor.getTelefono());
        dto.setEmail(proveedor.getEmail());
        dto.setDireccion(proveedor.getDireccion());
        dto.setFechaRegistro(proveedor.getFechaRegistro());
        dto.setActivo(proveedor.getActivo());
        
        return dto;
    }

    public Proveedor dtoToProveedor(ProveedorDTO proveedorDTO) {
        if (proveedorDTO == null) {
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setId(proveedorDTO.getId());
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setContacto(proveedorDTO.getContacto());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setDireccion(proveedorDTO.getDireccion());
        proveedor.setFechaRegistro(proveedorDTO.getFechaRegistro());
        proveedor.setActivo(proveedorDTO.getActivo());
        
        return proveedor;
    }

    // ===== INVENTARIO MAPPINGS =====
    public InventarioDTO inventarioToDTO(Inventario inventario) {
        if (inventario == null) {
            return null;
        }
        InventarioDTO dto = new InventarioDTO();
        dto.setId(inventario.getId());
        dto.setCantidadActual(inventario.getCantidadActual());
        dto.setStockMinimo(inventario.getStockMinimo());
        dto.setStockMaximo(inventario.getStockMaximo());
        dto.setUltimaActualizacion(inventario.getUltimaActualizacion());
        
        if (inventario.getProducto() != null) {
            dto.setProductoId(inventario.getProducto().getId());
            dto.setProductoNombre(inventario.getProducto().getNombre());
        }
        
        return dto;
    }

    public Inventario dtoToInventario(InventarioDTO inventarioDTO) {
        if (inventarioDTO == null) {
            return null;
        }
        Inventario inventario = new Inventario();
        inventario.setId(inventarioDTO.getId());
        inventario.setCantidadActual(inventarioDTO.getCantidadActual());
        inventario.setStockMinimo(inventarioDTO.getStockMinimo());
        inventario.setStockMaximo(inventarioDTO.getStockMaximo());
        inventario.setUltimaActualizacion(inventarioDTO.getUltimaActualizacion());
        
        return inventario;
    }

    // ===== ENTRADA_STOCK MAPPINGS =====
    public EntradaStockDTO entradaStockToDTO(EntradaStock entradaStock) {
        if (entradaStock == null) {
            return null;
        }
        EntradaStockDTO dto = new EntradaStockDTO();
        dto.setId(entradaStock.getId());
        dto.setCantidad(entradaStock.getCantidad());
        dto.setFechaEntrada(entradaStock.getFechaEntrada());
        dto.setUsuarioResponsable(entradaStock.getUsuarioResponsable());
        dto.setTipoEntrada(entradaStock.getTipoEntrada());
        dto.setLote(entradaStock.getLote());
        dto.setFechaVencimiento(entradaStock.getFechaVencimiento());
        
        if (entradaStock.getInventario() != null) {
            dto.setInventarioId(entradaStock.getInventario().getId());
            if (entradaStock.getInventario().getProducto() != null) {
                dto.setProductoNombre(entradaStock.getInventario().getProducto().getNombre());
            }
        }
        
        return dto;
    }

    public EntradaStock dtoToEntradaStock(EntradaStockDTO entradaStockDTO) {
        if (entradaStockDTO == null) {
            return null;
        }
        EntradaStock entradaStock = new EntradaStock();
        entradaStock.setId(entradaStockDTO.getId());
        entradaStock.setCantidad(entradaStockDTO.getCantidad());
        entradaStock.setFechaEntrada(entradaStockDTO.getFechaEntrada());
        entradaStock.setUsuarioResponsable(entradaStockDTO.getUsuarioResponsable());
        entradaStock.setTipoEntrada(entradaStockDTO.getTipoEntrada());
        entradaStock.setLote(entradaStockDTO.getLote());
        entradaStock.setFechaVencimiento(entradaStockDTO.getFechaVencimiento());
        
        return entradaStock;
    }

    // ===== SALIDA_STOCK MAPPINGS =====
    public SalidaStockDTO salidaStockToDTO(SalidaStock salidaStock) {
        if (salidaStock == null) {
            return null;
        }
        SalidaStockDTO dto = new SalidaStockDTO();
        dto.setId(salidaStock.getId());
        dto.setCantidad(salidaStock.getCantidad());
        dto.setFechaSalida(salidaStock.getFechaSalida());
        dto.setUsuarioResponsable(salidaStock.getUsuarioResponsable());
        dto.setMotivo(salidaStock.getMotivo());
        dto.setTipoSalida(salidaStock.getTipoSalida());
        
        if (salidaStock.getInventario() != null) {
            dto.setInventarioId(salidaStock.getInventario().getId());
            if (salidaStock.getInventario().getProducto() != null) {
                dto.setProductoNombre(salidaStock.getInventario().getProducto().getNombre());
            }
        }
        
        return dto;
    }

    public SalidaStock dtoToSalidaStock(SalidaStockDTO salidaStockDTO) {
        if (salidaStockDTO == null) {
            return null;
        }
        SalidaStock salidaStock = new SalidaStock();
        salidaStock.setId(salidaStockDTO.getId());
        salidaStock.setCantidad(salidaStockDTO.getCantidad());
        salidaStock.setFechaSalida(salidaStockDTO.getFechaSalida());
        salidaStock.setUsuarioResponsable(salidaStockDTO.getUsuarioResponsable());
        salidaStock.setMotivo(salidaStockDTO.getMotivo());
        salidaStock.setTipoSalida(salidaStockDTO.getTipoSalida());
        
        return salidaStock;
    }

    // ===== PRODUCTO_CATEGORIA MAPPINGS =====
    public ProductoCategoriaDTO productoCategoriaToDTO(ProductoCategoria productoCategoria) {
        if (productoCategoria == null) {
            return null;
        }
        ProductoCategoriaDTO dto = new ProductoCategoriaDTO();
        dto.setId(productoCategoria.getId());
        dto.setFechaAsignacion(productoCategoria.getFechaAsignacion());
        
        if (productoCategoria.getProducto() != null) {
            dto.setProductoId(productoCategoria.getProducto().getId());
            dto.setProductoNombre(productoCategoria.getProducto().getNombre());
        }
        
        if (productoCategoria.getCategoria() != null) {
            dto.setCategoriaId(productoCategoria.getCategoria().getId());
            dto.setCategoriaNombre(productoCategoria.getCategoria().getNombre());
        }
        
        return dto;
    }

    public ProductoCategoria dtoToProductoCategoria(ProductoCategoriaDTO productoCategoriaDTO) {
        if (productoCategoriaDTO == null) {
            return null;
        }
        ProductoCategoria productoCategoria = new ProductoCategoria();
        productoCategoria.setId(productoCategoriaDTO.getId());
        productoCategoria.setFechaAsignacion(productoCategoriaDTO.getFechaAsignacion());
        
        return productoCategoria;
    }

    // ===== PRODUCTO_PROVEEDOR MAPPINGS =====
    public ProductoProveedorDTO productoProveedorToDTO(ProductoProveedor productoProveedor) {
        if (productoProveedor == null) {
            return null;
        }
        ProductoProveedorDTO dto = new ProductoProveedorDTO();
        dto.setId(productoProveedor.getId());
        dto.setCosto(productoProveedor.getCosto());
        dto.setTiempoEntrega(productoProveedor.getTiempoEntrega());
        dto.setMoneda(productoProveedor.getMoneda());
        dto.setActivo(productoProveedor.getActivo());
        
        if (productoProveedor.getProducto() != null) {
            dto.setProductoId(productoProveedor.getProducto().getId());
            dto.setProductoNombre(productoProveedor.getProducto().getNombre());
        }
        
        if (productoProveedor.getProveedor() != null) {
            dto.setProveedorId(productoProveedor.getProveedor().getId());
            dto.setProveedorNombre(productoProveedor.getProveedor().getNombre());
        }
        
        return dto;
    }

    public ProductoProveedor dtoToProductoProveedor(ProductoProveedorDTO productoProveedorDTO) {
        if (productoProveedorDTO == null) {
            return null;
        }
        ProductoProveedor productoProveedor = new ProductoProveedor();
        productoProveedor.setId(productoProveedorDTO.getId());
        productoProveedor.setCosto(productoProveedorDTO.getCosto());
        productoProveedor.setTiempoEntrega(productoProveedorDTO.getTiempoEntrega());
        productoProveedor.setMoneda(productoProveedorDTO.getMoneda());
        productoProveedor.setActivo(productoProveedorDTO.getActivo());
        
        return productoProveedor;
    }
}
