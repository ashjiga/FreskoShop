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
);id_rol

-- Registro de intentos de login
CREATE TABLE login_log (
  id_log INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  fecha DATETIME NOT NULL,
  exito BOOLEAN,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);login_log

ALTER TABLE usuario ADD rol VARCHAR(20) NOT NULL DEFAULT 'CLIENTE';

