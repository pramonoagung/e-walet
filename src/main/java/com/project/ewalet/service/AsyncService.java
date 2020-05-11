package com.project.ewalet.service;

import org.apache.ibatis.session.SqlSession;
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
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailService emailService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public AsyncService() {
        restTemplate = new RestTemplate();
    }

    @Async("asyncExecutor")
    public CompletableFuture<JSONObject> sendEmail(String toEmail, String code) {
        String subject = "E-Walet";
        String body = "Your E-Walet verification code is " + code;
        try {
            emailService.sendMail(toEmail, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        return CompletableFuture.completedFuture(jsonObject);
    }
}
