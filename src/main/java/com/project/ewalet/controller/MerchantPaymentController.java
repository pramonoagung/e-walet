package com.project.ewalet.controller;

import com.project.ewalet.mapper.TopUpHistoryMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.payload.MerchantPaymentPayload;
import com.project.ewalet.model.TopUpHistory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MerchantPaymentController {

    @Autowired
    TopUpHistoryMapper topUpHistoryMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping(value = "confirm-merchant-topup/{token}")
    public ResponseEntity confirmMerchantTopUp(@PathVariable String token) {
        TopUpHistory lastTopUpHistory = topUpHistoryMapper.getLastHistoryByToken(token);
        JSONObject jsonResponse = new JSONObject();
        if (lastTopUpHistory == null){
            topUpHistoryMapper.updateStatus(1, lastTopUpHistory.getUser_id());
            jsonResponse.put("status", 200);
            jsonResponse.put("message", "Success");
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        else {
            jsonResponse.put("status", 406);
            jsonResponse.put("message", "Invalid payment token");
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }
    }
}
