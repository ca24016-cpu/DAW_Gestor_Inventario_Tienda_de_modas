package com.inventario.controller;

import com.inventario.dto.TallaDTO;
import com.inventario.service.TallaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ========== CONTROLLER: TALLA ==========
@RestController
@RequestMapping("/api/tallas")
@Tag(name = "Gestión de Tallas", description = "Endpoints para administrar las tallas de productos")
public class TallaController {

    @Autowired
    private TallaService tallaService;

    // GET: Listar todas las tallas
    @GetMapping
    @Operation(summary = "Listar todas las tallas", description = "Retorna una lista con todas las tallas registradas")
    public ResponseEntity<List<TallaDTO>> listarTodas() {
        List<TallaDTO> tallas = tallaService.obtenerTodas();
        return ResponseEntity.ok(tallas);
    }

    // GET: Obtener talla por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener talla por ID", description = "Busca una talla específica por su identificador")
    public ResponseEntity<TallaDTO> obtenerPorId(@PathVariable Long id) {
        TallaDTO talla = tallaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Talla no encontrada"));
        return ResponseEntity.ok(talla);
    }

    // GET: Obtener tallas por categoría
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Obtener tallas por categoría", description = "Busca tallas filtradas por categoría (INFANTIL/ADULTO)")
    public ResponseEntity<List<TallaDTO>> obtenerPorCategoria(@PathVariable String categoria) {
        List<TallaDTO> tallas = tallaService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(tallas);
    }

    // GET: Obtener tallas ordenadas
    @GetMapping("/ordenadas/todas")
    @Operation(summary = "Obtener tallas ordenadas", description = "Retorna las tallas ordenadas por su campo orden")
    public ResponseEntity<List<TallaDTO>> obtenerOrdenadas() {
        List<TallaDTO> tallas = tallaService.obtenerOrdenadas();
        return ResponseEntity.ok(tallas);
    }

    // POST: Crear nueva talla
    @PostMapping
    @Operation(summary = "Crear nueva talla", description = "Registra una nueva talla en el sistema")
    public ResponseEntity<TallaDTO> crear(@RequestBody TallaDTO tallaDTO) {
        TallaDTO nuevaTalla = tallaService.crear(tallaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTalla);
    }

    // PUT: Actualizar talla
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar talla", description = "Modifica los datos de una talla existente")
    public ResponseEntity<TallaDTO> actualizar(@PathVariable Long id, @RequestBody TallaDTO tallaDTO) {
        TallaDTO tallaActualizada = tallaService.actualizar(id, tallaDTO);
        return ResponseEntity.ok(tallaActualizada);
    }

    // DELETE: Eliminar talla
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar talla", description = "Elimina una talla del sistema")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tallaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
