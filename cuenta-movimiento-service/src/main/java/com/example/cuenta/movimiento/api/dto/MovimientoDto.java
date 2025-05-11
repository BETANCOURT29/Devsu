package com.example.cuenta.movimiento.api.dto;

import com.example.cuenta.movimiento.domain.model.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoDto {

    private Long id;
    private LocalDateTime fecha;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private TipoMovimiento tipoMovimiento;

    @NotNull(message = "El valor del movimiento es obligatorio")
    private Double valor;

    private Double saldo;

    @NotNull(message = "Debe especificar la cuenta")
    private Long cuentaId;
}
