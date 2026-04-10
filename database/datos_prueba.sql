-- =====================================
-- DATOS DE PRUEBA PARA SISTEMA INVENTARIO
-- =====================================

-- ============================
-- CATEGORIAS
-- ============================
INSERT INTO categoria (nombre, descripcion) VALUES
('Hombre', 'Ropa para hombre'),
('Mujer', 'Ropa para mujer');

INSERT INTO categoria (nombre, descripcion, id_categoria_padre, nivel) VALUES
('Camisas', 'Camisas casuales', 1, 2),
('Pantalones', 'Pantalones formales', 1, 2),
('Vestidos', 'Vestidos elegantes', 2, 2);

-- ============================
-- PRODUCTOS
-- ============================
-- Verifica IDs de talla antes si quieres:
-- SELECT * FROM talla;

INSERT INTO producto 
(nombre, descripcion, id_talla, color, marca, precio_unitario, sku) VALUES
('Camisa Azul Casual', 'Camisa manga larga', 14, 'Azul', 'Zara', 25.99, 'SKU001'),
('Pantalón Negro', 'Pantalón formal', 15, 'Negro', 'H&M', 35.50, 'SKU002'),
('Vestido Rojo', 'Vestido elegante', 13, 'Rojo', 'Forever21', 45.00, 'SKU003');

-- ============================
-- PROVEEDORES
-- ============================
INSERT INTO proveedor (nombre, contacto, telefono, email) VALUES
('Textiles El Salvador', 'Juan Pérez', '12345678', 'textiles@sv.com'),
('Moda Imports', 'Ana López', '87654321', 'moda@import.com');

-- ============================
-- PRODUCTO - CATEGORIA
-- ============================
INSERT INTO producto_categoria (id_producto, id_categoria) VALUES
(1, 3),
(2, 4),
(3, 5);

-- ============================
-- PRODUCTO - PROVEEDOR
-- ============================
INSERT INTO producto_proveedor (id_producto, id_proveedor, costo, tiempo_entrega) VALUES
(1, 1, 15.00, 5),
(2, 1, 20.00, 7),
(3, 2, 25.00, 10);

-- ============================
-- INVENTARIO
-- ============================
INSERT INTO inventario (id_producto, cantidad_actual, stock_minimo, stock_maximo) VALUES
(1, 10, 2, 50),
(2, 5, 2, 40),
(3, 8, 1, 30);

-- ============================
-- ENTRADAS DE STOCK
-- ============================
INSERT INTO entrada_stock (id_inventario, cantidad, usuario_responsable) VALUES
(1, 10, 'admin'),
(2, 5, 'admin'),
(3, 8, 'admin');

-- ============================
-- SALIDAS DE STOCK
-- ============================
INSERT INTO salida_stock (id_inventario, cantidad, usuario_responsable, motivo) VALUES
(1, 2, 'admin', 'Venta'),
(2, 1, 'admin', 'Venta');

-- =====================================
-- CONSULTAS DE VERIFICACIÓN
-- =====================================

-- Productos con talla
SELECT 
    p.nombre,
    t.nombre AS talla,
    t.categoria,
    p.precio_unitario
FROM producto p
JOIN talla t ON p.id_talla = t.id_talla;

-- Productos con categoría
SELECT 
    p.nombre AS producto,
    c.nombre AS categoria
FROM producto p
JOIN producto_categoria pc ON p.id_producto = pc.id_producto
JOIN categoria c ON pc.id_categoria = c.id_categoria;

-- Inventario actual
SELECT 
    p.nombre,
    i.cantidad_actual,
    i.stock_minimo
FROM inventario i
JOIN producto p ON i.id_producto = p.id_producto;

-- Productos con bajo stock
SELECT 
    p.nombre,
    i.cantidad_actual,
    i.stock_minimo
FROM inventario i
JOIN producto p ON i.id_producto = p.id_producto
WHERE i.cantidad_actual <= i.stock_minimo;

-- Proveedores por producto
SELECT 
    p.nombre,
    pr.nombre AS proveedor,
    pp.costo
FROM producto_proveedor pp
JOIN producto p ON pp.id_producto = p.id_producto
JOIN proveedor pr ON pp.id_proveedor = pr.id_proveedor;

-- Historial de entradas
SELECT 
    p.nombre,
    e.cantidad,
    e.fecha_entrada
FROM entrada_stock e
JOIN inventario i ON e.id_inventario = i.id_inventario
JOIN producto p ON i.id_producto = p.id_producto;

-- Historial de salidas
SELECT 
    p.nombre,
    s.cantidad,
    s.fecha_salida,
    s.motivo
FROM salida_stock s
JOIN inventario i ON s.id_inventario = i.id_inventario
JOIN producto p ON i.id_producto = p.id_producto;

-- =====================================
-- SIMULACIÓN REAL (VENTA)
-- =====================================

-- Registrar nueva salida
INSERT INTO salida_stock (id_inventario, cantidad, usuario_responsable, motivo)
VALUES (1, 1, 'admin', 'Venta adicional');

-- Actualizar inventario
UPDATE inventario
SET cantidad_actual = cantidad_actual - 1,
    ultima_actualizacion = CURRENT_TIMESTAMP
WHERE id_inventario = 1;

-- Ver resultado final
SELECT 
    p.nombre,
    i.cantidad_actual
FROM inventario i
JOIN producto p ON i.id_producto = p.id_producto;