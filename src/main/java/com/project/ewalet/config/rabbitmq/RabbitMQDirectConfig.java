package com.project.ewalet.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDirectConfig {

    @Bean
    Queue authQueue() {
        return new Queue("smsQueue", true);
    }

    @Bean
    Queue cartQueue() {
        return new Queue("emailQueue", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("otp-direct-exchange");
    }

    @Bean
    Binding authBinding(Queue authQueue, DirectExchange exchange) {
        return BindingBuilder.bind(authQueue).to(exchange).with("sms");
    }

    @Bean
    Binding cartBinding(Queue cartQueue, DirectExchange exchange) {
        return BindingBuilder.bind(cartQueue).to(exchange).with("email");
    }


}
