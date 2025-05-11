package com.example.cuenta.movimiento.infrastructure.messaging;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteCreadoEvent {

    private String clienteId;
    private String nombre;
    private String identificacion;
}
