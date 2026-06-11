package com.inventario.controller;

import com.inventario.dto.ProductoCategoriaDTO;
import com.inventario.service.ProductoCategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ========== CONTROLLER: PRODUCTO_CATEGORIA ==========
@RestController
@RequestMapping("/api/producto-categoria")
@Tag(name = "Gestión de Producto-Categoría", description = "Endpoints para administrar las relaciones entre productos y categorías")
public class ProductoCategoriaController {

    @Autowired
    private ProductoCategoriaService productoCategoriaService;

    // GET: Listar todas las relaciones
    @GetMapping
    @Operation(summary = "Listar todas las relaciones", description = "Retorna todas las relaciones producto-categoría registradas")
    public ResponseEntity<List<ProductoCategoriaDTO>> listarTodas() {
        List<ProductoCategoriaDTO> relaciones = productoCategoriaService.obtenerTodas();
        return ResponseEntity.ok(relaciones);
    }

    // GET: Obtener relación por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener relación por ID", description = "Busca una relación específica producto-categoría")
    public ResponseEntity<ProductoCategoriaDTO> obtenerPorId(@PathVariable Long id) {
        ProductoCategoriaDTO relacion = productoCategoriaService.obtenerPorId(id);
        return ResponseEntity.ok(relacion);
    }

    // GET: Obtener categorías de un producto
    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener categorías de un producto", description = "Retorna todas las categorías asignadas a un producto específico")
    public ResponseEntity<List<ProductoCategoriaDTO>> obtenerCategoriasPorProducto(@PathVariable Long productoId) {
        List<ProductoCategoriaDTO> categorias = productoCategoriaService.obtenerCategoriasPorProducto(productoId);
        return ResponseEntity.ok(categorias);
    }

    // GET: Obtener productos de una categoría
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Obtener productos de una categoría", description = "Retorna todos los productos asignados a una categoría específica")
    public ResponseEntity<List<ProductoCategoriaDTO>> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        List<ProductoCategoriaDTO> productos = productoCategoriaService.obtenerProductosPorCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }

    // POST: Asignar categoría a producto
    @PostMapping
    @Operation(summary = "Asignar categoría a producto", description = "Crea una nueva relación entre un producto y una categoría")
    public ResponseEntity<ProductoCategoriaDTO> asignarCategoria(@RequestBody ProductoCategoriaDTO productoCategoriaDTO) {
        ProductoCategoriaDTO nuevaRelacion = productoCategoriaService.crear(productoCategoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRelacion);
    }

    // DELETE: Eliminar relación
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar relación", description = "Desvincula un producto de una categoría")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoCategoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
