package com.project.ewalet.controller;

import com.project.ewalet.mapper.TopUpHistoryMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.MerchantPaymentDTO;
import com.project.ewalet.model.TopUpHistory;
import com.project.ewalet.model.User;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MerchantPaymentController {

    @Autowired
    TopUpHistoryMapper topUpHistoryMapper;

    @Autowired
    UserMapper userMapper;

    @PostMapping(value = "confirm-merchant-topup")
    public ResponseEntity confirmMerchantTopUp(@RequestBody MerchantPaymentDTO paymentToken, Authentication authentication) {
        User authenticatedUser = userMapper.findByPhoneNumber(authentication.getName());
        TopUpHistory topUpHistoryLatest = topUpHistoryMapper.findLatestRecordByDateAndUserId(authenticatedUser.getId(),
                8000 + authenticatedUser.getPhone_number());
        JSONObject jsonResponse = new JSONObject();
        if (topUpHistoryLatest.getToken() == paymentToken.getToken()){
            topUpHistoryMapper.updateStatus(1, authenticatedUser.getId());
            jsonResponse.put("status", 200);
            jsonResponse.put("message", "Success");
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        else {
            jsonResponse.put("status", 409);
            jsonResponse.put("message", "Invalid Token");
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }
}
