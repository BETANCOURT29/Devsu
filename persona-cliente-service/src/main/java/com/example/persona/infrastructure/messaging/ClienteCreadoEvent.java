package com.example.persona.infrastructure.messaging;

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
