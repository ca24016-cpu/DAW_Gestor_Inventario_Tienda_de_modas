-- ============================
-- TABLA: TALLA (Catálogo)
-- ============================
CREATE TABLE talla (
    id_talla INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    categoria VARCHAR(20) NOT NULL CHECK (categoria IN ('INFANTIL','ADULTO')),
    orden INTEGER NOT NULL,
    UNIQUE (nombre, categoria)
);

-- ============================
-- DATOS INICIALES: TALLAS
-- ============================
INSERT INTO talla (nombre, categoria, orden) VALUES
-- Infantiles
('0-3M', 'INFANTIL', 1),
('3-6M', 'INFANTIL', 2),
('6-12M', 'INFANTIL', 3),
('12-18M', 'INFANTIL', 4),
('2T', 'INFANTIL', 5),
('3T', 'INFANTIL', 6),
('4T', 'INFANTIL', 7),
('5T', 'INFANTIL', 8),

-- Niños
('XS', 'INFANTIL', 9),
('S', 'INFANTIL', 10),
('M', 'INFANTIL', 11),
('L', 'INFANTIL', 12),

-- Adultos
('S', 'ADULTO', 13),
('M', 'ADULTO', 14),
('L', 'ADULTO', 15),
('XL', 'ADULTO', 16),
('XXL', 'ADULTO', 17);

-- ============================
-- TABLA: CATEGORIA
-- ============================
CREATE TABLE categoria (
    id_categoria INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    id_categoria_padre INTEGER,
    nivel INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_categoria_padre
        FOREIGN KEY (id_categoria_padre)
        REFERENCES categoria(id_categoria)
);

-- ============================
-- TABLA: PRODUCTO
-- ============================
CREATE TABLE producto (
    id_producto INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),

    id_talla INTEGER,

    color VARCHAR(30),
    marca VARCHAR(50),

    precio_unitario NUMERIC(10,2) NOT NULL CHECK (precio_unitario >= 0),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    estado VARCHAR(20) DEFAULT 'ACTIVO'
        CHECK (estado IN ('ACTIVO','INACTIVO','DESCONTINUADO')),

    sku VARCHAR(50) UNIQUE,

    CONSTRAINT fk_producto_talla
        FOREIGN KEY (id_talla)
        REFERENCES talla(id_talla)
);

-- ============================
-- TABLA: PROVEEDOR
-- ============================
CREATE TABLE proveedor (
    id_proveedor INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(255),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- ============================
-- TABLA: INVENTARIO (GLOBAL)
-- ============================
CREATE TABLE inventario (
    id_inventario INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_producto INTEGER NOT NULL UNIQUE,

    cantidad_actual INTEGER DEFAULT 0 CHECK (cantidad_actual >= 0),
    stock_minimo INTEGER DEFAULT 0 CHECK (stock_minimo >= 0),
    stock_maximo INTEGER CHECK (stock_maximo >= 0),

    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_inventario_producto
        FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto)
);

-- ============================
-- TABLA: PRODUCTO - CATEGORIA
-- ============================
CREATE TABLE producto_categoria (
    id_producto INTEGER NOT NULL,
    id_categoria INTEGER NOT NULL,
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id_producto, id_categoria),

    CONSTRAINT fk_prodcat_producto
        FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto),

    CONSTRAINT fk_prodcat_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)
);

-- ============================
-- TABLA: PRODUCTO - PROVEEDOR
-- ============================
CREATE TABLE producto_proveedor (
    id_producto INTEGER NOT NULL,
    id_proveedor INTEGER NOT NULL,

    costo NUMERIC(10,2) NOT NULL CHECK (costo >= 0),
    tiempo_entrega INTEGER DEFAULT 0 CHECK (tiempo_entrega >= 0),
    moneda VARCHAR(3) DEFAULT 'USD',
    activo BOOLEAN DEFAULT TRUE,

    PRIMARY KEY (id_producto, id_proveedor),

    CONSTRAINT fk_prodprov_producto
        FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto),

    CONSTRAINT fk_prodprov_proveedor
        FOREIGN KEY (id_proveedor)
        REFERENCES proveedor(id_proveedor)
);

-- ============================
-- TABLA: ENTRADA STOCK
-- ============================
CREATE TABLE entrada_stock (
    id_entrada INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_inventario INTEGER NOT NULL,

    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    fecha_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_responsable VARCHAR(100) NOT NULL,

    tipo_entrada VARCHAR(50) DEFAULT 'COMPRA'
        CHECK (tipo_entrada IN ('COMPRA','AJUSTE','DEVOLUCION')),

    lote VARCHAR(100),
    fecha_vencimiento DATE,

    CONSTRAINT fk_entrada_inventario
        FOREIGN KEY (id_inventario)
        REFERENCES inventario(id_inventario)
);

-- ============================
-- TABLA: SALIDA STOCK
-- ============================
CREATE TABLE salida_stock (
    id_salida INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_inventario INTEGER NOT NULL,

    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    fecha_salida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_responsable VARCHAR(100) NOT NULL,

    motivo VARCHAR(255) NOT NULL,

    tipo_salida VARCHAR(50) DEFAULT 'VENTA'
        CHECK (tipo_salida IN ('VENTA','AJUSTE','DONACION','PERDIDA')),

    CONSTRAINT fk_salida_inventario
        FOREIGN KEY (id_inventario)
        REFERENCES inventario(id_inventario)
);

-- ============================
-- ÍNDICES
-- ============================
CREATE INDEX idx_producto_nombre ON producto(nombre);
CREATE INDEX idx_producto_estado ON producto(estado);
CREATE INDEX idx_proveedor_nombre ON proveedor(nombre);
CREATE INDEX idx_inventario_producto ON inventario(id_producto);
CREATE INDEX idx_entrada_fecha ON entrada_stock(fecha_entrada);
CREATE INDEX idx_salida_fecha ON salida_stock(fecha_salida);
CREATE INDEX idx_categoria_padre ON categoria(id_categoria_padre);
CREATE INDEX idx_producto_talla ON producto(id_talla);

-- Índices compuestos
CREATE INDEX idx_inventario_stock ON inventario(cantidad_actual);
CREATE INDEX idx_producto_precio ON producto(precio_unitario, estado);