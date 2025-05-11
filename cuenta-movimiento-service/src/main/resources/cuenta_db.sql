CREATE DATABASE IF NOT EXISTS cuenta_db;

CREATE TABLE cuentas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(255) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(255) NOT NULL,
    saldo_inicial DOUBLE NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id VARCHAR(255) NOT NULL
);

CREATE TABLE movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME(6) NOT NULL,
    tipo_movimiento ENUM('DEPOSITO', 'RETIRO') NOT NULL,
    valor DOUBLE NOT NULL,
    saldo DOUBLE NOT NULL,
    cuenta_id BIGINT NOT NULL,
    CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuentas(id)
);

INSERT INTO cuentas (id, numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES
(1, '478758', 'Ahorros', 2000, true, 'jose.lema'),
(2, '225487', 'Corriente', 100, true, 'marianela.montalvo'),
(3, '495878', 'Ahorros', 0, true, 'juan.osorio'),
(4, '496825', 'Ahorros', 540, true, 'marianela.montalvo'),
(5, '585545', 'Corriente', 1000, true, 'jose.lema');

INSERT INTO movimientos (id, fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
(1, '2022-02-10 10:00:00', 'RETIRO', 575, 1425, 1),
(2, '2022-02-10 12:00:00', 'DEPOSITO', 600, 700, 2),
(3, '2022-02-10 14:00:00', 'DEPOSITO', 150, 150, 3),
(4, '2022-02-08 10:00:00', 'RETIRO', 540, 0, 4);
