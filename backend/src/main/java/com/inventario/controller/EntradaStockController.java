package com.inventario.controller;

import com.inventario.dto.EntradaStockDTO;
import com.inventario.service.EntradaStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ========== CONTROLLER: ENTRADA_STOCK ==========
@RestController
@RequestMapping("/api/entrada-stock")
@Tag(name = "Gestión de Entrada de Stock", description = "Endpoints para registrar entrada de productos al inventario")
public class EntradaStockController {

    @Autowired
    private EntradaStockService entradaStockService;

    // GET: Listar todas las entradas
    @GetMapping
    @Operation(summary = "Listar todas las entradas", description = "Retorna el historial de todas las entradas de stock")
    public ResponseEntity<List<EntradaStockDTO>> listarTodas() {
        List<EntradaStockDTO> entradas = entradaStockService.obtenerTodas();
        return ResponseEntity.ok(entradas);
    }

    // GET: Obtener entrada por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener entrada por ID", description = "Busca un registro específico de entrada de stock")
    public ResponseEntity<EntradaStockDTO> obtenerPorId(@PathVariable Long id) {
        EntradaStockDTO entrada = entradaStockService.obtenerPorId(id);
        return ResponseEntity.ok(entrada);
    }

    // GET: Obtener entradas por inventario
    @GetMapping("/inventario/{inventarioId}")
    @Operation(summary = "Obtener entradas de un inventario", description = "Retorna todas las entradas asociadas a un inventario específico")
    public ResponseEntity<List<EntradaStockDTO>> obtenerPorInventario(@PathVariable Long inventarioId) {
        List<EntradaStockDTO> entradas = entradaStockService.obtenerPorInventario(inventarioId);
        return ResponseEntity.ok(entradas);
    }

    // GET: Obtener entradas por tipo
    @GetMapping("/tipo/{tipoEntrada}")
    @Operation(summary = "Obtener entradas por tipo", description = "Retorna las entradas filtradas por tipo (COMPRA, DEVOLUCION, etc)")
    public ResponseEntity<List<EntradaStockDTO>> obtenerPorTipo(@PathVariable String tipoEntrada) {
        List<EntradaStockDTO> entradas = entradaStockService.obtenerPorTipo(tipoEntrada);
        return ResponseEntity.ok(entradas);
    }

    // POST: Registrar nueva entrada de stock
    @PostMapping
    @Operation(summary = "Registrar entrada de stock", description = "Crea un nuevo registro de entrada y actualiza el inventario automáticamente")
    public ResponseEntity<EntradaStockDTO> registrarEntrada(@RequestBody EntradaStockDTO entradaStockDTO) {
        EntradaStockDTO nuevaEntrada = entradaStockService.crear(entradaStockDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEntrada);
    }
}
