package com.inventario.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// ========== ENTIDAD: SALIDA_STOCK ==========
// Registro de salida de stock del inventario
@Entity
@Table(name = "salida_stock")
public class SalidaStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salida")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    @Column(name = "usuario_responsable", nullable = false)
    private String usuarioResponsable;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    @Column(name = "tipo_salida")
    private String tipoSalida;

    // Constructores
    public SalidaStock() {
        this.fechaSalida = LocalDateTime.now();
        this.tipoSalida = "VENTA";
    }

    public SalidaStock(Inventario inventario, Integer cantidad, String usuarioResponsable, String motivo, String tipoSalida) {
        this.inventario = inventario;
        this.cantidad = cantidad;
        this.usuarioResponsable = usuarioResponsable;
        this.motivo = motivo;
        this.tipoSalida = tipoSalida;
        this.fechaSalida = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(String tipoSalida) {
        this.tipoSalida = tipoSalida;
    }
}
