-- =========================
-- INSERTS BLOOM_STUDIO_DB
-- =========================

-- ROLES
INSERT INTO roles (nombre_rol) VALUES
('ADMIN'),
('CLIENTE'),
('EMPLEADO');

-- USUARIOS
INSERT INTO usuarios (nombre, email, contrasena, id_rol) VALUES
('Administrador', 'admin@bloodstudio.com', 'admin123', 1),
('Juan Perez', 'juan@gmail.com', 'juan123', 2),
('Maria Lopez', 'maria@gmail.com', 'maria123', 2),
('Jesus Carranza', 'jesusc@gmail.com', 'jesus123', 2),
('karla Martinez', 'Karlam@gmail.com', 'Karla123', 2);

-- CATEGORIAS
INSERT INTO categorias (nombre_categoria) VALUES
('Playeras'),
('Sudaderas'),
('Accesorios'),
('Servicios Digitales'),
('Tags'),
('Vasos'),
('Temporada'),
('Stickers');

-- COLORES
INSERT INTO colores (nombre_color, codigo_hex) VALUES
('Negro', '#000000'),
('Blanco', '#FFFFFF'),
('Gris', '#808080'),
('Azul', '#0000FF'),
('Rosa', '#FFC0CB'),
('Verde', '#008f39');
-- TALLAS
INSERT INTO tallas (nombre_talla) VALUES
('S'),
('M'),
('L'),
('XL');

-- PRODUCTOS
INSERT INTO productos (sku, nombre, descripcion, precio, img_url) VALUES
('PLY-BASIC-01', 'Playera Básica', 'Playera de algodón unisex', 249.00, 'img/playera_basica.jpg'),
('SUD-HOOD-01', 'Sudadera Hoodie', 'Sudadera con gorro', 599.00, 'img/sudadera_hoodie.jpg'),
('SER-DIG-01', 'Invitaciones', 'Invitaciones personalizadas', 150.00, 'img/invitacion.jpg'),
('VASO-BASIC-01', 'Vaso Cafetero', 'Vaso Cafetero Azul', 45.00, 'img/vasoazul.jpg'),
('ALBUM-PAREJA-01', 'Album para pareja', 'Album para 150 fotos', 250.00, 'img/albumpareja.jpg'),
('STICKERS-LAPICES-01', 'Paquete etiquetas lapices', 'Paquete con 24 piezas', 70.00, 'img/stickerslapices.jpg');

-- PRODUCTO_CATEGORIA
INSERT INTO producto_categoria (id_producto, id_categoria) VALUES
(1, 1), -- Playera -> Playeras
(2, 2), -- Sudadera -> Sudaderas
(3,4),
(4,6),
(5,7),
(6,8);


-- ETIQUETAS
INSERT INTO etiquetas (nombre_etiqueta) VALUES
('Nuevo'),
('Oferta'),
('Popular');

-- PRODUCTO_ETIQUETA
INSERT INTO producto_etiqueta (id_producto, id_etiqueta) VALUES
(1, 1), -- Playera -> Nuevo
(1, 3), -- Playera -> Popular
(2, 1), -- Sudadera -> Nuevo
(3,3),
(4,3),
(5,3),
(6,2);

-- INVENTARIO (Variantes)
INSERT INTO inventario (id_producto, id_color, id_talla, cantidad) VALUES
-- Playera Básica
(1, 1, 1, 5),   -- Negra S
(1, 1, 2, 10),  -- Negra M
(1, 1, 3, 8),   -- Negra L
(1, 2, 2, 12),  -- Blanca M
(1, 3, 2, 6),   -- Gris M
-- Sudadera Hoodie
(2, 1, 2, 4),   -- Negra M
(2, 1, 3, 2),   -- Negra L
(2, 2, 3, 5),   -- Blanca L
(2, 1, 2, 2),
(3, NULL, NULL, 2),   
(4, 4, NULL, 15),
(5, NULL, NULL, 4),
(6, NULL, NULL, 5);

-- PEDIDOS
INSERT INTO pedidos (id_usuario, total, estado_pedido) VALUES
(2, 498.00, 'pagado'),
(3, 599.00, 'pendiente'),
(3, 250.00, 'pendiente'),
(4, 50.00, 'pagado'),
(5, 250.00, 'pagado'),
(5, 50.00, 'pendiente');

-- DETALLE_PEDIDO
INSERT INTO detalle_pedido (id_pedido, id_inventario, cantidad, precio_unitario) VALUES
(1, 2, 2, 249.00), -- Juan compra 2 Playeras Negras M
(2, 6, 1, 599.00), -- Maria compra 1 Sudadera Negra M
(6, 6, 3, 550.00),
(5, 5, 3, 200.00),
(4, 2, 4, 450.00),
(3, 2, 2, 249.00);