package com.inventario.controller;

import com.inventario.dto.CategoriaDTO;
import com.inventario.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ========== CONTROLLER: CATEGORIA ==========
@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Gestión de Categorías", description = "Endpoints para administrar las categorías de productos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // GET: Listar todas las categorías
    @GetMapping
    @Operation(summary = "Listar todas las categorías", description = "Retorna una lista con todas las categorías registradas")
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        List<CategoriaDTO> categorias = categoriaService.obtenerTodas();
        return ResponseEntity.ok(categorias);
    }

    // GET: Obtener categoría por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Busca una categoría específica por su identificador")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

    // GET: Obtener categorías activas
    @GetMapping("/activas/todas")
    @Operation(summary = "Obtener categorías activas", description = "Retorna solo las categorías activas")
    public ResponseEntity<List<CategoriaDTO>> obtenerActivas() {
        List<CategoriaDTO> categorias = categoriaService.obtenerActivas();
        return ResponseEntity.ok(categorias);
    }

    // GET: Obtener subcategorías
    @GetMapping("/{id}/subcategorias")
    @Operation(summary = "Obtener subcategorías", description = "Retorna las subcategorías de una categoría padre")
    public ResponseEntity<List<CategoriaDTO>> obtenerSubcategorias(@PathVariable Long id) {
        List<CategoriaDTO> subcategorias = categoriaService.obtenerSubcategorias(id);
        return ResponseEntity.ok(subcategorias);
    }

    // POST: Crear nueva categoría
    @PostMapping
    @Operation(summary = "Crear nueva categoría", description = "Registra una nueva categoría de productos")
    public ResponseEntity<CategoriaDTO> crear(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO nuevaCategoria = categoriaService.crear(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    // PUT: Actualizar categoría
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Modifica los datos de una categoría existente")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaActualizada = categoriaService.actualizar(id, categoriaDTO);
        return ResponseEntity.ok(categoriaActualizada);
    }

    // DELETE: Eliminar categoría
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría del sistema")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
