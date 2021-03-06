package com.project.ewalet.service.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQPublisher {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private String exchange = "otp-direct-exchange-prod";

    public void mqSendSms(String phoneNumber) {
        amqpTemplate.convertAndSend(exchange, "sms-prod", phoneNumber);
        System.out.println("Send auth message " + phoneNumber);
    }

    public void mqSendEmail(String email) {
        amqpTemplate.convertAndSend(exchange, "email-prod", email);
        System.out.println("Send cart item message : " + email);
    }
}
