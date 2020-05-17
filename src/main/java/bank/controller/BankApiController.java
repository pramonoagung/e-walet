package bank.controller;

import bank.service.AsyncService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class BankApiController {

    @Autowired
    private static AsyncService service;

    @GetMapping("/api/confirm-bank/{token}")
    public ResponseEntity<?> cofirmTopUp(@PathVariable String token) {
        JSONObject jsonObject = new JSONObject();
        System.out.println("BANK CONFIRMED");
        CompletableFuture<JSONObject> response = service.confirmPayment(token);
        CompletableFuture.allOf(response).isDone();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
