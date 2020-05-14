package com.project.ewalet.service.rabbitmq;

//import com.example.springboot.mapper.ItemMapper;
//import com.example.springboot.mapper.TransactionMapper;
//import com.example.springboot.mapper.UserMapper;
//import com.example.springboot.model.Cart;
//import com.example.springboot.model.Item;
//import com.example.springboot.model.Transaction;
//import com.example.springboot.model.User;
//import com.example.springboot.repository.RedisRepository;
import com.project.ewalet.service.AsyncService;
import com.project.ewalet.service.SmsService;
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

    @Autowired
    AsyncService asyncService;

    @Autowired
    SmsService smsService;

    @RabbitListener(queues = "smsQueue-prod")
    public void receiveSms(String message) {
        System.out.println((++counter) + " receive sms message : " + message);
        JSONObject jsonMessage = messageEncoder(message);
        asyncService.sendSms(jsonMessage.get("phoneNumber").toString(), jsonMessage.get("otpCode").toString());
    }

    @RabbitListener(queues = "emailQueue-prod")
    public void receiveEmail(String message) {
        System.out.println((++counter) + "receive email message : " + message);
        JSONObject jsonMessage = messageEncoder(message);
        asyncService.sendEmail(jsonMessage.get("toEmail").toString(), jsonMessage.get("otpCode").toString());
    }

    public JSONObject messageEncoder(String message){
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage = (JSONObject) new JSONParser().parse(message);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonMessage;
    }
}
