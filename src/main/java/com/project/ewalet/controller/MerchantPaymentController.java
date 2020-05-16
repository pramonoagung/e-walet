package com.project.ewalet.controller;

import com.project.ewalet.mapper.PaymentMethodMapper;
import com.project.ewalet.mapper.TopUpHistoryMapper;
import com.project.ewalet.mapper.UserBalanceMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.UserBalance;
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
    UserBalanceMapper userBalanceMapper;

    @Autowired
    PaymentMethodMapper paymentMethodMapper;

    @GetMapping(value = "confirm-merchant-topup/{token}/{invoice_id}")
    public ResponseEntity confirmMerchantTopUp(@PathVariable String token, @PathVariable long invoice_id) {
        TopUpHistory topUpHistory = topUpHistoryMapper.getTopUpHistoryById(invoice_id);
        JSONObject jsonResponse = new JSONObject();
        if (topUpHistory.getStatus() == 0 && topUpHistory.getToken().equals(token)
                && paymentMethodMapper.getById(topUpHistory.getPayment_method()).getPayment_type() == 2) {
            if (topUpHistory != null) {
                topUpHistoryMapper.updateStatusById(1, topUpHistory.getId());
                UserBalance userBalance = userBalanceMapper.findByUserId(topUpHistory.getUser_id());
                if (userBalance != null) {
                    userBalanceMapper.updateUserBalance(topUpHistory.getTopup_balance() + userBalance.getBalance(), topUpHistory.getUser_id());
                } else {
                    UserBalance newUserBalance = new UserBalance();
                    newUserBalance.setUser_id(topUpHistory.getUser_id());
                    newUserBalance.setBalance(topUpHistory.getTopup_balance());
                    userBalanceMapper.insert(newUserBalance);
                }
                jsonResponse.put("status", 200);
                jsonResponse.put("message", "Success");
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                jsonResponse.put("status", 406);
                jsonResponse.put("message", "Invalid payment token");
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        else {
            jsonResponse.put("status", 406);
            jsonResponse.put("message", "Payment request for payment token " + token + " not available");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
