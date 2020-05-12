package com.project.ewalet.controller;

import com.project.ewalet.mapper.*;
import com.project.ewalet.model.BalanceCatalog;
import com.project.ewalet.model.PaymentMethod;
import com.project.ewalet.model.TopUpHistory;
import com.project.ewalet.model.User;
import com.project.ewalet.model.payload.TopUpRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@CrossOrigin
public class TopUpController {

    @Autowired
    private BalanceCatalogMapper balanceCatalogMapper;
    @Autowired
    private PaymentMethodMapper paymentMethodMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TopUpHistoryMapper topUpHistoryMapper;

    @PostMapping("/topup-balance")
    public ResponseEntity<?> topup(@RequestBody TopUpRequest topUpRequest) {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        BalanceCatalog balanceCatalog = balanceCatalogMapper.findByCode(topUpRequest.getCode());
        PaymentMethod paymentMethod = paymentMethodMapper.findByCode(topUpRequest.getPayment_type());
        User user = userMapper.findByPhoneNumber(topUpRequest.getPhone_number());


        //record to db
        TopUpHistory topUpHistory = new TopUpHistory();
        topUpHistory.setUser_id(user.getId());
        topUpHistory.setTopup_balance(balanceCatalog.getBalance());
        topUpHistory.setToken(8000 + user.getPhone_number());
        topUpHistory.setPayment_method(paymentMethod.getPayment_type());
        topUpHistory.setStatus(0);
        topUpHistory.setCreated_at(getCurrentTime());
        topUpHistoryMapper.insert(topUpHistory);

        TopUpHistory topUpHistoryLatest = topUpHistoryMapper.findLatestRecordByDateAndUserId(user.getId(),
                8000 + user.getPhone_number());

        data.put("topup_balance", balanceCatalog.getBalance());
        data.put("payment_type", paymentMethod.getPayment_type());
        data.put("invoice_id", topUpHistoryLatest.getId());
        data.put("token", topUpHistoryLatest.getToken());
        data.put("status", topUpHistoryLatest.getStatus());

        jsonObject.put("status", 200);
        jsonObject.put("data", data);
        jsonObject.put("message", "success");

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
