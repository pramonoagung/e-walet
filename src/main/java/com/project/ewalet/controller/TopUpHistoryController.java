package com.project.ewalet.controller;

import com.project.ewalet.mapper.TopUpHistoryMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.TopUpHistory;
import com.project.ewalet.model.User;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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
        ArrayList<TopUpHistory> userTopUpHistory = topUpHistoryMapper.getTopUpHistoryByUserId(user.getId());

        jsonResponse.put("status", 200);
        jsonResponse.put("data", userTopUpHistory);
        jsonResponse.put("message", "success");

        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
