package com.example.springboot.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private String exchange = "store-direct-exchange";

    public void sendLoginAuth(String auth) {
        amqpTemplate.convertAndSend(exchange, "auth", auth);
        System.out.println("Send auth message " + auth);
    }

    public void sendCartItem(String cartItem) {
        amqpTemplate.convertAndSend(exchange, "cart", cartItem);
        System.out.println("Send cart item message : " + cartItem);
    }

    public void sendPaymentConfirmation(String userId) {
        amqpTemplate.convertAndSend(exchange, "payment", userId);
        System.out.println("Send cart item message : " + userId);
    }
}
