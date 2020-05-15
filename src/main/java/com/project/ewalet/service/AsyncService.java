package com.project.ewalet.service;

import com.project.ewalet.model.payload.SmsGatewayPayload;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    public AsyncService() {
//        restTemplate = new RestTemplate();
//    }

    @Async("asyncExecutor")
    public CompletableFuture<SmsGatewayPayload> sendSms(String phoneNumber, String otpCode) {
        SmsGatewayPayload response = smsService.sendSms(phoneNumber, otpCode);
        return CompletableFuture.completedFuture(response);
    }

    @Async("asyncExecutor")
    public CompletableFuture<JSONObject> sendEmail(String toEmail, String code) {
        JSONObject jsonObject = new JSONObject();
        String subject = "E-Walet";
        String body = "Your E-Walet verification code is " + code;
        try {
            emailService.sendMail(toEmail, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        jsonObject.put("status", 200);
        return CompletableFuture.completedFuture(jsonObject);

    }
}
