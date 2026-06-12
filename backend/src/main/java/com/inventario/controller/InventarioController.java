package com.inventario.controller;

import com.inventario.dto.InventarioDTO;
import com.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ========== CONTROLLER: INVENTARIO ==========
@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Gestión de Inventario", description = "Endpoints para administrar el inventario de stock")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    // GET: Listar todo el inventario
    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Retorna el estado de stock de todos los productos")
    public ResponseEntity<List<InventarioDTO>> listarTodo() {
        List<InventarioDTO> inventarios = inventarioService.obtenerTodos();
        return ResponseEntity.ok(inventarios);
    }

    // GET: Obtener inventario por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener inventario por ID", description = "Busca el inventario de un producto específico")
    public ResponseEntity<InventarioDTO> obtenerPorId(@PathVariable Long id) {
        InventarioDTO inventario = inventarioService.obtenerPorId(id);
        return ResponseEntity.ok(inventario);
    }

    // GET: Obtener inventario por ID de producto
    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener inventario por producto", description = "Busca el inventario usando el ID del producto")
    public ResponseEntity<InventarioDTO> obtenerPorProductoId(@PathVariable Long productoId) {
        InventarioDTO inventario = inventarioService.obtenerPorProductoId(productoId);
        return ResponseEntity.ok(inventario);
    }

    // GET: Obtener productos con stock bajo
    @GetMapping("/bajo-stock/todos")
    @Operation(summary = "Productos con stock bajo", description = "Retorna los productos cuyo stock está por debajo del mínimo")
    public ResponseEntity<List<InventarioDTO>> obtenerProductosStockBajo() {
        List<InventarioDTO> productosStockBajo = inventarioService.obtenerProductosStockBajo();
        return ResponseEntity.ok(productosStockBajo);
    }

    // POST: Crear nuevo inventario
    @PostMapping
    @Operation(summary = "Crear nuevo inventario", description = "Registra el inventario inicial de un producto")
    public ResponseEntity<InventarioDTO> crear(@RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO nuevoInventario = inventarioService.crear(inventarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInventario);
    }

    // PUT: Actualizar inventario
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inventario", description = "Modifica los parámetros de stock de un inventario")
    public ResponseEntity<InventarioDTO> actualizar(@PathVariable Long id, @RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO inventarioActualizado = inventarioService.actualizar(id, inventarioDTO);
        return ResponseEntity.ok(inventarioActualizado);
    }

    // PATCH: Actualizar cantidad de stock
    @PatchMapping("/{id}/cantidad")
    @Operation(summary = "Actualizar cantidad de stock", description = "Modifica la cantidad actual de un inventario")
    public ResponseEntity<InventarioDTO> actualizarCantidad(@PathVariable Long id, @RequestParam Integer nuevaCantidad) {
        InventarioDTO inventarioActualizado = inventarioService.actualizarCantidad(id, nuevaCantidad);
        return ResponseEntity.ok(inventarioActualizado);
    }
}
