package bank.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public AsyncService() {
        restTemplate = new RestTemplate();
    }

    @Async("asyncExecutor")
    public CompletableFuture<JSONObject> confirmPayment(String token) {
        JSONObject response = restTemplate.getForObject("http://336f1b2a.ngrok.io/confirm-bank/"+token, JSONObject.class);
        if (response != null) {
            return CompletableFuture.completedFuture(response);
        } else {
            return CompletableFuture.completedFuture(null);
        }
    }
}
