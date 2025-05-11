package com.example.cuenta.movimiento.infrastructure.messaging;

import com.example.cuenta.movimiento.application.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitConsumer {

    private final CuentaService cuentaService;

    @RabbitListener(queues = "cliente.creado.queue")
    public void escucharClienteCreado(ClienteCreadoEvent event) {
        System.out.println("Evento recibido: " + event.getClienteId());

        cuentaService.crearCuentaInicialParaCliente(event);
    }
}
