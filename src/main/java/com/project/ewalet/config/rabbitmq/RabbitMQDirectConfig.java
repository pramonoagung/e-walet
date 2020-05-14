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
    Queue smsQueue() {
        return new Queue("smsQueue-prod", true);
    }

    @Bean
    Queue emailQueue() {
        return new Queue("emailQueue-prod", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("otp-direct-exchange-prod");
    }

    @Bean
    Binding smsBinding(Queue smsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(smsQueue).to(exchange).with("sms-prod");
    }

    @Bean
    Binding emailBinding(Queue emailQueue, DirectExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with("email-prod");
    }


}
