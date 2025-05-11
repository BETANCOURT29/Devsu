package com.example.cuenta.movimiento.domain.repository;


import com.example.cuenta.movimiento.domain.model.Cuenta;
import com.example.cuenta.movimiento.domain.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaAndFechaBetween(Cuenta cuenta, LocalDateTime desde, LocalDateTime hasta);

    List<Movimiento> findByCuenta(Cuenta cuenta);
}
