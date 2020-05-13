package com.project.ewalet.service.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQPublisher {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private String exchange = "otp-direct-exchange";

    public void sendLoginAuth(String auth) {
        amqpTemplate.convertAndSend(exchange, "sms", auth);
        System.out.println("Send auth message " + auth);
    }

    public void sendCartItem(String cartItem) {
        amqpTemplate.convertAndSend(exchange, "email", cartItem);
        System.out.println("Send cart item message : " + cartItem);
    }
}
