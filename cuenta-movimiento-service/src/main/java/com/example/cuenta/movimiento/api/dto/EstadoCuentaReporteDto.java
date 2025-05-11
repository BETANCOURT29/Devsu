package com.example.cuenta.movimiento.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoCuentaReporteDto {

    private String clienteId;
    private List<CuentaResumenDto> cuentas;
}
