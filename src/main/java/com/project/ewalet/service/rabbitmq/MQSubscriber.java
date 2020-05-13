package com.project.ewalet.service.rabbitmq;

import com.example.springboot.mapper.ItemMapper;
import com.example.springboot.mapper.TransactionMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.model.Cart;
import com.example.springboot.model.Item;
import com.example.springboot.model.Transaction;
import com.example.springboot.model.User;
import com.example.springboot.repository.RedisRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class MQSubscriber {
    public static int counter = 1;

    @RabbitListener(queues = "smsQueue")
    public void receiveSms(String message) {

    }


    @RabbitListener(queues = "emailQueue")
    public void receiveEmail(String message) {

    }
}
