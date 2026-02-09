-- Creación de la Base de Datos
CREATE DATABASE IF NOT EXISTS bloom_studio_db;
USE bloom_studio_db;

-- 1. TABLA ROLES
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL, -- Ej: ADMIN, CLIENTE
    estatus BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. TABLA USUARIOS
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL, -- Se debe guardar encriptada
    estatus BOOLEAN DEFAULT TRUE,
    id_rol INT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- 3. TABLA CATEGORIAS
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    estatus BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 4. TABLA COLORES (Definida)
CREATE TABLE colores (
    id_color INT AUTO_INCREMENT PRIMARY KEY,
    nombre_color VARCHAR(50) NOT NULL, -- Ej: Rojo, Negro
    codigo_hex VARCHAR(7), -- Ej: #FF0000 (Útil para mostrar la bolita de color en el frontend)
    estatus BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 5. TABLA TALLAS (Definida)
CREATE TABLE tallas (
    id_talla INT AUTO_INCREMENT PRIMARY KEY,
    nombre_talla VARCHAR(10) NOT NULL, -- Ej: S, M, L, XL, 28, 32
    estatus BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 6. TABLA PRODUCTOS (Modificada: Quitamos cantidad, dejamos precio base)
CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) UNIQUE, -- Código único general
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    img_url VARCHAR(255), -- Ruta de la imagen principal
    estado_producto ENUM('activo', 'agotado', 'descontinuado') DEFAULT 'activo',
    estatus BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 7. TABLA INVENTARIO (La magia para manejar tallas y colores)
CREATE TABLE inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT,
    id_color INT,
    id_talla INT,
    cantidad INT NOT NULL DEFAULT 0, -- Aquí sabemos cuántas hay de cada variante
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_color) REFERENCES colores(id_color),
    FOREIGN KEY (id_talla) REFERENCES tallas(id_talla)
);

-- 8. TABLA PRODUCTO_CATEGORIA (Relación Muchos a Muchos)
CREATE TABLE producto_categoria (
    id_producto INT,
    id_categoria INT,
    PRIMARY KEY (id_producto, id_categoria),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

-- 9. ETIQUETAS
CREATE TABLE etiquetas (
    id_etiqueta INT AUTO_INCREMENT PRIMARY KEY,
    nombre_etiqueta VARCHAR(50) NOT NULL, -- Ej: Nuevo, Oferta
    estatus BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 10. PRODUCTO_ETIQUETA
CREATE TABLE producto_etiqueta (
    id_producto INT,
    id_etiqueta INT,
    PRIMARY KEY (id_producto, id_etiqueta),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
    FOREIGN KEY (id_etiqueta) REFERENCES etiquetas(id_etiqueta)
);

-- 11. TABLA PEDIDOS (Cabecera de la compra)
CREATE TABLE pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL,
    estado_pedido ENUM('pendiente', 'pagado', 'enviado', 'entregado') DEFAULT 'pendiente',
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- 12. DETALLE DE PEDIDO (Qué productos compró exactamente)
CREATE TABLE detalle_pedido (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_inventario INT, -- Hacemos referencia al inventario específico (Talla/Color exacto)
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL, -- Precio al momento de la compra
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    FOREIGN KEY (id_inventario) REFERENCES inventario(id_inventario)
);

-- TABLA PARA RECUPERACIÓN DE CONTRASEÑAS
CREATE TABLE tokens_recuperacion (
    id_token INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL, -- El código único que se envía por correo
    id_usuario INT NOT NULL,
    fecha_expiracion DATETIME NOT NULL, -- Cuándo deja de valer este código (ej: 15 min después)
    usado TINYINT(1) DEFAULT 0, -- 0: No usado, 1: Ya se usó para cambiar la pass
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);