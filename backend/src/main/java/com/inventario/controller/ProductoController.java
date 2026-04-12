package com.inventario.controller;

import com.inventario.dto.ProductoDTO;
import com.inventario.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Gestión de Productos", description = "Endpoints para administrar el inventario de la tienda de moda")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // 1. GET: Listar todos los productos
    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Retorna una lista con todos los productos registrados")
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        List<ProductoDTO> productos = productoService.obtenerTodos(); // Método corregido
        return ResponseEntity.ok(productos);
    }

    // 2. GET: Buscar por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico por su identificador")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        // El servicio devuelve Optional, así que usamos .orElseThrow para manejarlo
        ProductoDTO producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ResponseEntity.ok(producto);
    }

    // 3. POST: Crear nuevo producto
    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Registra un nuevo producto en el inventario")
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProducto = productoService.crear(productoDTO); // Método corregido
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // 4. PUT: Actualizar producto
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO productoActualizado = productoService.actualizar(id, productoDTO); // Método corregido
        return ResponseEntity.ok(productoActualizado);
    }

    // 5. DELETE: Eliminar producto
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del inventario")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id); // Método corregido
        return ResponseEntity.noContent().build();
    }
}
