package com.example.cuenta.movimiento.api.dto;

import com.example.cuenta.movimiento.domain.model.TipoMovimiento;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoResumenDto {

    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    private Double valor;
    private Double saldo;
}
