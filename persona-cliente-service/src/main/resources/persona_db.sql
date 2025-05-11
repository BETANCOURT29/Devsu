CREATE DATABASE IF NOT EXISTS persona_db;

CREATE TABLE personas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    genero VARCHAR(255) NOT NULL,
    edad INT NOT NULL,
    identificacion VARCHAR(255) NOT NULL UNIQUE,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NOT NULL
);

CREATE TABLE clientes (
    persona_id BIGINT PRIMARY KEY,
    cliente_id VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT fk_cliente_persona FOREIGN KEY (persona_id) REFERENCES personas(id)
);

INSERT INTO personas (id, nombre, genero, edad, identificacion, direccion, telefono) VALUES
(1, 'Jose Lema', 'M', 30, '1092837465', 'Otavalo sn y principal', '098254785'),
(2, 'Marianela Montalvo', 'F', 28, '1092837466', 'Amazonas y NNUU', '097548965'),
(3, 'Juan Osorio', 'M', 33, '1092837467', '13 junio y Equinoccial', '098874587');

INSERT INTO clientes (persona_id, cliente_id, contrasena, estado) VALUES
(1, 'jose.lema', '1234', true),
(2, 'marianela.montalvo', '5678', true),
(3, 'juan.osorio', '1245', true);
