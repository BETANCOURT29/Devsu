package com.example.cuenta.movimiento.api.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaResumenDto {

    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoDisponible;
    private List<MovimientoResumenDto> movimientos;
}
