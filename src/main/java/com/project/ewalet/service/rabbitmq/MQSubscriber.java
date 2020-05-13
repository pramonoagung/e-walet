package com.example.springboot.service;

import com.example.springboot.mapper.ItemMapper;
import com.example.springboot.mapper.TransactionMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.model.Cart;
import com.example.springboot.model.Item;
import com.example.springboot.model.Transaction;
import com.example.springboot.model.User;
import com.example.springboot.repository.RedisRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class RabbitMQConsumer {
    public static int counter = 1;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    RedisRepository redisRepository;

    @RabbitListener(queues = "authQueue")
    public void receiveAuth(String message) {
        System.out.println("Received auth message from RabbitMQ: " + message);
        User auth = authValidation(message);
        if (auth == null) {
            System.out.println("Credentials failed");
        }
        userMapper.login(auth.getId());
        System.out.println("Credentials login success");
    }

    public User authValidation(String message) {
        String[] auth = message.split(" ");
        return userMapper.authValidation(auth[0], auth[1]);
    }

    @RabbitListener(queues = "cartQueue")
    public void receiveCart(String message) throws ParseException {
        System.out.println("Received cart message from RabbitMQ: " + message);
        JSONObject jsonCart = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        jsonCart = (JSONObject) jsonParser.parse(message);
        System.out.println(jsonCart);
        System.out.println(jsonCart.get("userId"));
        Cart addCart = new Cart("" + counter + "", jsonCart.get("userId").toString(), jsonCart.get("itemId").toString(), Integer.parseInt(jsonCart.get("qty").toString()));
        redisRepository.add(addCart);
        System.out.println("get id cart  " + addCart.getId());
        counter++;
        System.out.println("get id 1 " + redisRepository.findCart("1"));
        System.out.println("get id 2 " + redisRepository.findCart("2"));
        System.out.println("get id 3 " + redisRepository.findCart("3"));

        System.out.println("GET ALL " + redisRepository.findAllCarts());
    }

    @RabbitListener(queues = "paymentQueue")
    public void receivePayment(String message) throws InterruptedException {
        System.out.println("Receive payment message from RabbitMQ: " + message);
        int userId = Integer.parseInt(message);
        //check login status
        if (!userMapper.getById(userId).isLoginStatus()) {
            System.out.println("Payment failed");
        }
        Map map = redisRepository.findAllCarts();
        System.out.println(map);
        System.out.println(redisRepository.findCart("1"));
        CompletableFuture<Transaction> insertToDb = insertTransaction(userId);
        CompletableFuture<Transaction> sendNotification = sendNotification(userId);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Transaction> insertTransaction(int userId) throws InterruptedException {
        System.out.println("INSERT TRANSACTION INTO DATABASE " + userId);

        Cart cartLoop;
        int counter = 0;
        do {
            cartLoop = redisRepository.findCart(Integer.toString(counter));
            System.out.println(cartLoop);
            if (Integer.parseInt(cartLoop.getUserId()) == userId) {
                User user = userMapper.getById(userId);
                Item item = itemMapper.getById(Integer.parseInt(cartLoop.getItemId()));
                Transaction transaction = new Transaction();
                transaction.setUserId((long)userId);
                transaction.setItemId(item.getId());
                transaction.setUserName(user.getName());
                transaction.setItemName(item.getName());
                transaction.setPrice(item.getPrice());
                transaction.setQuantity(cartLoop.getQty());
                System.out.println(transaction);
                System.out.println("COUNTER " +counter);
                System.out.println("CARTS SIZE " + redisRepository.findAllCarts().size());
                transactionMapper.insert(transaction);
            }
            cartLoop = new Cart();
            counter++;
            System.out.println("COUNTER INSIDE LOOP"+counter);
            redisRepository.delete(cartLoop.getId());
        } while (counter < redisRepository.findAllCarts().size());
        System.out.println("COUNTER AFTER LOOP"+counter);
        counter = 0;
        Transaction transaction = new Transaction();
        return CompletableFuture.completedFuture(transaction);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Transaction> sendNotification(int userId) throws InterruptedException {
        User user = userMapper.getById(userId);
        System.out.println("SEND NOTIFICATION TO " + user.getEmail());
        System.out.println("Payment for user " + user.getName() + " is success");
        Transaction transaction = new Transaction();
        return CompletableFuture.completedFuture(transaction);
    }

}
