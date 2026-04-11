package com.inventario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inventario.entity.Producto;
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

