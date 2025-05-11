package com.example.cuenta.movimiento.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "cliente.exchange";
    public static final String QUEUE = "cliente.creado.queue";
    public static final String ROUTING_KEY = "cliente.creado";

    @Bean
    public DirectExchange clienteExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue clienteCreadoQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding bindingClienteCreado() {
        return BindingBuilder
                .bind(clienteCreadoQueue())
                .to(clienteExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put(
                "com.example.persona.infrastructure.messaging.ClienteCreadoEvent",
                com.example.cuenta.movimiento.infrastructure.messaging.ClienteCreadoEvent.class
        );

        classMapper.setTrustedPackages("*");
        classMapper.setIdClassMapping(idClassMapping);
        converter.setClassMapper(classMapper);

        return converter;
    }


}
