package com.example.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDirectConfig {

    @Bean
    Queue authQueue() {
        return new Queue("authQueue", true);
    }

    @Bean
    Queue cartQueue() {
        return new Queue("cartQueue", true);
    }

    @Bean
    Queue paymentQueue() {
        return new Queue("paymentQueue", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("store-direct-exchange");
    }

    @Bean
    Binding authBinding(Queue authQueue, DirectExchange exchange) {
        return BindingBuilder.bind(authQueue).to(exchange).with("auth");
    }

    @Bean
    Binding cartBinding(Queue cartQueue, DirectExchange exchange) {
        return BindingBuilder.bind(cartQueue).to(exchange).with("cart");
    }

    @Bean
    Binding paymentBinding(Queue paymentQueue, DirectExchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange).with("payment");
    }

}
