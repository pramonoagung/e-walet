package com.project.ewalet.controller;

import com.project.ewalet.mapper.TopUpHistoryMapper;
import com.project.ewalet.mapper.UserBalanceMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.TopUpHistory;
import com.project.ewalet.model.UserBalance;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankPaymentController {

    @Autowired
    private UserBalanceMapper userBalanceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TopUpHistoryMapper topUpHistoryMapper;

    @GetMapping("/confirm-bank/{token}")
    public ResponseEntity<?> paymentConfirmation(@PathVariable String token) {

        TopUpHistory topUpHistory = topUpHistoryMapper.getLastHistoryByToken(token);
        topUpHistoryMapper.updateStatus(1, topUpHistory.getUser_id());

        UserBalance userBalance = userBalanceMapper.findByUserId(topUpHistory.getUser_id());
        long actualBalance = userBalance.getBalance();
        long currentBalance = topUpHistory.getTopup_balance() + actualBalance;

        //update user balance
        userBalanceMapper.updateUserBalance(currentBalance, topUpHistory.getUser_id());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("message", "e-walet has been topped up with Rp " + topUpHistory.getTopup_balance());

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
