package com.example.persona.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new Jackson2JsonMessageConverter();
    }
}
