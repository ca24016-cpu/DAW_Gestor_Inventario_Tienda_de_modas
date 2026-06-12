package com.inventario.controller;

import com.inventario.dto.SalidaStockDTO;
import com.inventario.service.SalidaStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ========== CONTROLLER: SALIDA_STOCK ==========
@RestController
@RequestMapping("/api/salida-stock")
@Tag(name = "Gestión de Salida de Stock", description = "Endpoints para registrar salida de productos del inventario")
public class SalidaStockController {

    @Autowired
    private SalidaStockService salidaStockService;

    // GET: Listar todas las salidas
    @GetMapping
    @Operation(summary = "Listar todas las salidas", description = "Retorna el historial de todas las salidas de stock")
    public ResponseEntity<List<SalidaStockDTO>> listarTodas() {
        List<SalidaStockDTO> salidas = salidaStockService.obtenerTodas();
        return ResponseEntity.ok(salidas);
    }

    // GET: Obtener salida por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener salida por ID", description = "Busca un registro específico de salida de stock")
    public ResponseEntity<SalidaStockDTO> obtenerPorId(@PathVariable Long id) {
        SalidaStockDTO salida = salidaStockService.obtenerPorId(id);
        return ResponseEntity.ok(salida);
    }

    // GET: Obtener salidas por inventario
    @GetMapping("/inventario/{inventarioId}")
    @Operation(summary = "Obtener salidas de un inventario", description = "Retorna todas las salidas asociadas a un inventario específico")
    public ResponseEntity<List<SalidaStockDTO>> obtenerPorInventario(@PathVariable Long inventarioId) {
        List<SalidaStockDTO> salidas = salidaStockService.obtenerPorInventario(inventarioId);
        return ResponseEntity.ok(salidas);
    }

    // GET: Obtener salidas por tipo
    @GetMapping("/tipo/{tipoSalida}")
    @Operation(summary = "Obtener salidas por tipo", description = "Retorna las salidas filtradas por tipo (VENTA, PERDIDA, etc)")
    public ResponseEntity<List<SalidaStockDTO>> obtenerPorTipo(@PathVariable String tipoSalida) {
        List<SalidaStockDTO> salidas = salidaStockService.obtenerPorTipo(tipoSalida);
        return ResponseEntity.ok(salidas);
    }

    // POST: Registrar nueva salida de stock
    @PostMapping
    @Operation(summary = "Registrar salida de stock", description = "Crea un nuevo registro de salida y actualiza el inventario automáticamente (valida stock disponible)")
    public ResponseEntity<SalidaStockDTO> registrarSalida(@RequestBody SalidaStockDTO salidaStockDTO) {
        SalidaStockDTO nuevaSalida = salidaStockService.crear(salidaStockDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSalida);
    }
}
