package com.project.ewalet.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class MQConsumer {
    private static final String TASK_QUEUE_NAME = "ewalet";
    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;

    @Autowired
    private static EmailService emailService;

    public MQConsumer() {
    }

    public static void main() throws Exception {
        init();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println("[*] Waiting for messages. To exit press CTRL+C");
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Received '" + message + "'");
            try {
                decode(message);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("[x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });
    }

    private static void init() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private static void decode(String message) throws ParseException {
        MQConsumer mqConsumer = new MQConsumer();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(message);
        if (Integer.valueOf(json.get("key").toString()) == 1) {
        } else if (Integer.valueOf(json.get("key").toString()) == 2) {

        }
    }
}
