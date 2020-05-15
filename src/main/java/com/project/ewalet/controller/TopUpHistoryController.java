package com.project.ewalet.controller;

import com.project.ewalet.mapper.TopUpHistoryMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.TopUpHistory;
import com.project.ewalet.model.User;
import com.project.ewalet.model.payload.TopUpHistoryPayload;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class TopUpHistoryController {

    @Autowired
    TopUpHistoryMapper topUpHistoryMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping(value = "get-topup-history")
    ResponseEntity<?> getTopupHistoryByUserPhoneNumber(Authentication authentication) {
        JSONObject jsonResponse = new JSONObject();

        User user = userMapper.findByPhoneNumber(authentication.getName());
        List<TopUpHistoryPayload> userTopUpHistoryBank = topUpHistoryMapper.getTopupHistoryBanksByUserId(user.getId());
        List<TopUpHistoryPayload> userTopUpHistoryMerchant = topUpHistoryMapper.getTopupHistoryMerchantsByUserId(user.getId());
        List<TopUpHistoryPayload> userTopUpHistoryResponse = null;
        System.out.println(userTopUpHistoryBank);
        System.out.println(userTopUpHistoryMerchant);
        userTopUpHistoryBank.addAll(userTopUpHistoryMerchant);
        System.out.println(userTopUpHistoryBank);
//        System.out.println(userTopUpHistoryResponse.addAll(userTopUpHistoryBank));
//        System.out.println(userTopUpHistoryResponse.addAll(userTopUpHistoryMerchant));
//        System.out.println(userTopUpHistoryResponse);
        jsonResponse.put("status", 200);
        jsonResponse.put("data", userTopUpHistoryBank);
        jsonResponse.put("message", "success");

        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
