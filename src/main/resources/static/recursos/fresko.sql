-- Eliminar y crear la base de datos
DROP SCHEMA IF EXISTS fresko;
DROP USER IF EXISTS 'usuario_fresko'@'%';
CREATE SCHEMA fresko;

-- Crear usuario y asignar permisos
CREATE USER 'usuario_fresko'@'%' IDENTIFIED BY 'ClaveFresko2025.';
GRANT ALL PRIVILEGES ON fresko.* TO 'usuario_fresko'@'%';
FLUSH PRIVILEGES;

USE fresko;

-- Tabla de categorías
CREATE TABLE categoria (
  id_categoria INT AUTO_INCREMENT PRIMARY KEY,
  descripcion VARCHAR(50) NOT NULL,
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN
);

-- Tabla de productos
CREATE TABLE producto (
  id_producto INT AUTO_INCREMENT PRIMARY KEY,
  id_categoria INT NOT NULL,
  descripcion VARCHAR(100) NOT NULL,
  detalle TEXT,
  precio DOUBLE,
  existencias INT,
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN,
  FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

-- Tabla de usuarios
CREATE TABLE usuario (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(512) NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  apellidos VARCHAR(80) NOT NULL,
  correo VARCHAR(100),
  telefono VARCHAR(20),
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN,
  rol VARCHAR(30)
);

-- Tabla de roles
CREATE TABLE rol (
  id_rol INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(30),
  id_usuario INT,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla de productos favoritos
CREATE TABLE favorito (
  id_favorito INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  id_producto INT NOT NULL,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Carrito de compras temporal
CREATE TABLE carrito (
  id_carrito INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  id_producto INT NOT NULL,
  cantidad INT NOT NULL DEFAULT 1,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Tabla de facturas
CREATE TABLE factura (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  fecha DATE,
  total DOUBLE,
  estado INT,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Detalles de cada venta dentro de una factura
CREATE TABLE venta (
  id_venta INT AUTO_INCREMENT PRIMARY KEY,
  id_factura INT NOT NULL,
  id_producto INT NOT NULL,
  precio DOUBLE,
  cantidad INT,
  FOREIGN KEY (id_factura) REFERENCES factura(id_factura),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Tabla para permisos por URL
CREATE TABLE request_matcher (
  id_request_matcher INT AUTO_INCREMENT PRIMARY KEY,
  pattern VARCHAR(255) NOT NULL,
  role_name VARCHAR(50) NOT NULL
);

-- Tabla para rutas accesibles para todos
CREATE TABLE request_matcher_all (
  id_request_matcher INT AUTO_INCREMENT PRIMARY KEY,
  pattern VARCHAR(255) NOT NULL
);

-- Registro de intentos de login
CREATE TABLE login_log (
  id_log INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  fecha DATETIME NOT NULL,
  exito BOOLEAN,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Querys para añadir informacion e imagenes importantes - RECALCO ES IMPORTANTE!!! jaja (hay imagenes de las secciones del index)
USE fresko;

INSERT INTO categoria (descripcion, ruta_imagen, activo) VALUES
('Abarrotes', 'https://imgur.com/r1tPOdO', true),
('Carnes', 'https://imgur.com/gBzuv9W', true),
('Bebidas', 'https://imgur.com/ErAY1JK', true),
('Limpieza', 'https://imgur.com/RBHK0qu', true),
('Mascotas', 'https://imgur.com/MfTrJpy', true);

INSERT INTO producto (id_categoria, descripcion, detalle, precio, existencias, ruta_imagen, activo) VALUES
(1, 'Arroz 1kg', 'Arroz blanco empacado', 900, 100, 'https://walmartcr.vtexassets.com/arquivos/ids/610493-500-auto?v=638515680492000000&width=500&height=auto&aspect=true', true),
(2, 'Pechuga de pollo', 'Fresca y sin hueso', 3500, 50, 'https://walmartcr.vtexassets.com/arquivos/ids/530578-1200-900?v=638419994116070000&width=1200&height=900&aspect=true', true),
(3, 'Refresco de cola 2L', 'Bebida gaseosa sabor cola', 1200, 80, 'https://walmartcr.vtexassets.com/arquivos/ids/463812-500-auto?v=638328299978570000&width=500&height=auto&aspect=true', true),
(4, 'Jabón líquido', 'Para limpieza de superficies', 1800, 60, 'https://walmartcr.vtexassets.com/arquivos/ids/753322-500-500?v=638655000591400000&width=500&height=auto&aspect=true', true),
(5, 'Alimento para perro 3kg', 'Marca Canino Feliz', 7200, 40, 'https://walmartcr.vtexassets.com/arquivos/ids/893600-500-auto?v=638781696147970000&width=500&height=auto&aspect=true', true);

-- Usuario con rol USER (puede agregar favoritos el pass de usuario1 es "usuario123")
INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo, rol)
VALUES ('usuario1', '{bcrypt}$2a$10$wH3MeHf5czjcD82vJWfvS.1FNhI.3U9aFo8U0iEkGp5FfbKAdASuG', 'Carlos', 'Fernández', 
'carlos@correo.com', '8888-8888', '/img/user1.png', true, 'ROLE_CLIENTE');

-- Asignación de rol USER
INSERT INTO rol (nombre, id_usuario) VALUES ('ROLE_USER', 1);

INSERT INTO favorito (id_usuario, id_producto) VALUES
(1, 1),
(1, 3),
(1, 5);

-- Usuario con rol ADMIN (el pass de admin es "admin123")
INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo, rol)
VALUES ('admin', '{bcrypt}$2a$10$7d8eSdrfrf1Nq6tQYZ9TeOOjCjE1g6yJWsg0NfToXQxwQ8YoV8M5y', 'Admin', 'Principal',
'admin@fresko.com', '9999-9999', '/img/admin.png', true, 'ROLE_ADMIN');

-- Asignación de rol ADMIN
INSERT INTO rol (nombre, id_usuario) VALUES ('ROLE_ADMIN', 2);

