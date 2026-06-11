package com.inventario.controller;

import com.inventario.dto.ProductoProveedorDTO;
import com.inventario.service.ProductoProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ========== CONTROLLER: PRODUCTO_PROVEEDOR ==========
@RestController
@RequestMapping("/api/producto-proveedor")
@Tag(name = "Gestión de Producto-Proveedor", description = "Endpoints para administrar las relaciones entre productos y proveedores")
public class ProductoProveedorController {

    @Autowired
    private ProductoProveedorService productoProveedorService;

    // GET: Listar todas las relaciones
    @GetMapping
    @Operation(summary = "Listar todas las relaciones", description = "Retorna todas las relaciones producto-proveedor registradas")
    public ResponseEntity<List<ProductoProveedorDTO>> listarTodas() {
        List<ProductoProveedorDTO> relaciones = productoProveedorService.obtenerTodas();
        return ResponseEntity.ok(relaciones);
    }

    // GET: Obtener relación por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener relación por ID", description = "Busca una relación específica producto-proveedor")
    public ResponseEntity<ProductoProveedorDTO> obtenerPorId(@PathVariable Long id) {
        ProductoProveedorDTO relacion = productoProveedorService.obtenerPorId(id);
        return ResponseEntity.ok(relacion);
    }

    // GET: Obtener proveedores de un producto
    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener proveedores de un producto", description = "Retorna todos los proveedores asociados a un producto específico")
    public ResponseEntity<List<ProductoProveedorDTO>> obtenerProveedoresPorProducto(@PathVariable Long productoId) {
        List<ProductoProveedorDTO> proveedores = productoProveedorService.obtenerProveedoresPorProducto(productoId);
        return ResponseEntity.ok(proveedores);
    }

    // GET: Obtener productos de un proveedor
    @GetMapping("/proveedor/{proveedorId}")
    @Operation(summary = "Obtener productos de un proveedor", description = "Retorna todos los productos suministrados por un proveedor específico")
    public ResponseEntity<List<ProductoProveedorDTO>> obtenerProductosPorProveedor(@PathVariable Long proveedorId) {
        List<ProductoProveedorDTO> productos = productoProveedorService.obtenerProductosPorProveedor(proveedorId);
        return ResponseEntity.ok(productos);
    }

    // GET: Obtener relaciones activas de un producto
    @GetMapping("/producto/{productoId}/activos")
    @Operation(summary = "Obtener proveedores activos de un producto", description = "Retorna solo los proveedores activos de un producto específico")
    public ResponseEntity<List<ProductoProveedorDTO>> obtenerProveedoresActivosPorProducto(@PathVariable Long productoId) {
        List<ProductoProveedorDTO> proveedores = productoProveedorService.obtenerProveedoresActivosPorProducto(productoId);
        return ResponseEntity.ok(proveedores);
    }

    // POST: Asignar proveedor a producto
    @PostMapping
    @Operation(summary = "Asignar proveedor a producto", description = "Crea una nueva relación entre un producto y un proveedor con costo y tiempo de entrega")
    public ResponseEntity<ProductoProveedorDTO> asignarProveedor(@RequestBody ProductoProveedorDTO productoProveedorDTO) {
        ProductoProveedorDTO nuevaRelacion = productoProveedorService.crear(productoProveedorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRelacion);
    }

    // PUT: Actualizar relación
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar relación", description = "Modifica el costo, tiempo de entrega u otros datos de la relación")
    public ResponseEntity<ProductoProveedorDTO> actualizar(@PathVariable Long id, @RequestBody ProductoProveedorDTO productoProveedorDTO) {
        ProductoProveedorDTO relacionActualizada = productoProveedorService.actualizar(id, productoProveedorDTO);
        return ResponseEntity.ok(relacionActualizada);
    }

    // DELETE: Eliminar relación
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar relación", description = "Desvincula un producto de un proveedor")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoProveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH: Cambiar estado de relación
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de relación", description = "Activa o desactiva una relación producto-proveedor")
    public ResponseEntity<ProductoProveedorDTO> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        ProductoProveedorDTO relacionActualizada = productoProveedorService.cambiarEstado(id, activo);
        return ResponseEntity.ok(relacionActualizada);
    }
}
