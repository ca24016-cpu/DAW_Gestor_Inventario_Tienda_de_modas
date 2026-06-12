package com.inventario.controller;

import com.inventario.dto.ProveedorDTO;
import com.inventario.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ========== CONTROLLER: PROVEEDOR ==========
@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Gestión de Proveedores", description = "Endpoints para administrar los proveedores de la tienda")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // GET: Listar todos los proveedores
    @GetMapping
    @Operation(summary = "Listar todos los proveedores", description = "Retorna una lista con todos los proveedores registrados")
    public ResponseEntity<List<ProveedorDTO>> listarTodos() {
        List<ProveedorDTO> proveedores = proveedorService.obtenerTodos();
        return ResponseEntity.ok(proveedores);
    }

    // GET: Obtener proveedor por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID", description = "Busca un proveedor específico por su identificador")
    public ResponseEntity<ProveedorDTO> obtenerPorId(@PathVariable Long id) {
        ProveedorDTO proveedor = proveedorService.obtenerPorId(id);
        return ResponseEntity.ok(proveedor);
    }

    // GET: Obtener proveedores activos
    @GetMapping("/activos/todos")
    @Operation(summary = "Obtener proveedores activos", description = "Retorna solo los proveedores activos")
    public ResponseEntity<List<ProveedorDTO>> obtenerActivos() {
        List<ProveedorDTO> proveedores = proveedorService.obtenerActivos();
        return ResponseEntity.ok(proveedores);
    }

    // POST: Crear nuevo proveedor
    @PostMapping
    @Operation(summary = "Crear nuevo proveedor", description = "Registra un nuevo proveedor en el sistema")
    public ResponseEntity<ProveedorDTO> crear(@RequestBody ProveedorDTO proveedorDTO) {
        ProveedorDTO nuevoProveedor = proveedorService.crear(proveedorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
    }

    // PUT: Actualizar proveedor
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proveedor", description = "Modifica los datos de un proveedor existente")
    public ResponseEntity<ProveedorDTO> actualizar(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO) {
        ProveedorDTO proveedorActualizado = proveedorService.actualizar(id, proveedorDTO);
        return ResponseEntity.ok(proveedorActualizado);
    }

    // DELETE: Eliminar proveedor
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor del sistema")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH: Cambiar estado de proveedor
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de proveedor", description = "Activa o desactiva un proveedor")
    public ResponseEntity<ProveedorDTO> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        ProveedorDTO proveedorActualizado = proveedorService.cambiarEstado(id, activo);
        return ResponseEntity.ok(proveedorActualizado);
    }
}
