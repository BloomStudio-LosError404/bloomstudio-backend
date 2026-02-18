DROP DATABASE IF EXISTS bloom_studio_db;
CREATE DATABASE bloom_studio_db;
USE bloom_studio_db;

-- 1. ROLES
CREATE TABLE roles (
    id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. USUARIOS
CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    estatus TINYINT(1) DEFAULT 1,
    id_rol BIGINT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- 3. TOKENS RECUPERACION (Para "Olvidé mi contraseña")
CREATE TABLE tokens_recuperacion (
    id_token BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    id_usuario BIGINT NOT NULL,
    fecha_expiracion DATETIME NOT NULL,
    usado TINYINT(1) DEFAULT 0,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- 4. CATEGORIAS
CREATE TABLE categorias (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    estatus TINYINT(1) DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 5. COLORES
CREATE TABLE colores (
    id_color BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_color VARCHAR(50) NOT NULL,
    codigo_hex VARCHAR(7),
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 6. TALLAS
CREATE TABLE tallas (
    id_talla BIGINT AUTO_INCREMENT PRIMARY KEY,
nombre_talla VARCHAR(10) NOT NULL, -- Ej: S, M, L, XL, 28, 32 
estatus BOOLEAN DEFAULT TRUE, 
fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP, 
fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 7. PRODUCTOS
CREATE TABLE productos (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    img_url VARCHAR(255),
    estado_producto ENUM('activo', 'agotado', 'descontinuado') DEFAULT 'activo',
    estatus TINYINT(1) DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 8. INVENTARIO (Variantes de productos)
CREATE TABLE inventario (
    id_inventario BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT,
    id_color BIGINT,
    id_talla BIGINT,
    cantidad INT NOT NULL DEFAULT 0, 
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_color) REFERENCES colores(id_color),
    FOREIGN KEY (id_talla) REFERENCES tallas(id_talla)
);

-- 9. PRODUCTO_CATEGORIA (Relación Muchos a Muchos)
CREATE TABLE producto_categoria (
    id_producto BIGINT,
    id_categoria BIGINT,
    PRIMARY KEY (id_producto, id_categoria),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

-- 10. ETIQUETAS
CREATE TABLE etiquetas (
    id_etiqueta BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_etiqueta VARCHAR(50) NOT NULL,
    estatus TINYINT(1) DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 11. PRODUCTO_ETIQUETA (Relación Muchos a Muchos)
CREATE TABLE producto_etiqueta (
    id_producto BIGINT,
    id_etiqueta BIGINT,
    PRIMARY KEY (id_producto, id_etiqueta),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_etiqueta) REFERENCES etiquetas(id_etiqueta)
);

-- 12. PEDIDOS (Cabecera)
CREATE TABLE pedidos (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL,
    estado_pedido ENUM('pendiente', 'pagado', 'enviado', 'entregado') DEFAULT 'pendiente',
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- 13. DETALLE_PEDIDO (Renglones del pedido)
CREATE TABLE detalle_pedido (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT,
    id_inventario BIGINT, 
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    FOREIGN KEY (id_inventario) REFERENCES inventario(id_inventario)
);
