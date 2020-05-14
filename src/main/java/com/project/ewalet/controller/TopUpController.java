package com.project.ewalet.controller;

import com.project.ewalet.mapper.BalanceCatalogMapper;
import com.project.ewalet.mapper.PaymentMethodMapper;
import com.project.ewalet.mapper.TopUpHistoryMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.BalanceCatalog;
import com.project.ewalet.model.PaymentMethod;
import com.project.ewalet.model.TopUpHistory;
import com.project.ewalet.model.User;
import com.project.ewalet.model.payload.TopUpRequest;
import com.project.ewalet.utils.Utility;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
    @Autowired
    private Utility utility;

    @PostMapping("/topup-balance")
    public ResponseEntity<?> topup(@RequestBody TopUpRequest topUpRequest) {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        BalanceCatalog balanceCatalog = balanceCatalogMapper.findByCode(topUpRequest.getCode());
        PaymentMethod paymentMethod = paymentMethodMapper.getById(topUpRequest.getPayment_method_id());
        User user = userMapper.findByPhoneNumber(topUpRequest.getPhone_number());

        //record to db
        TopUpHistory topUpHistory = new TopUpHistory();
        topUpHistory.setUser_id(user.getId());
        topUpHistory.setTopup_balance(balanceCatalog.getBalance());
        topUpHistory.setToken(8000 + user.getPhone_number());
        topUpHistory.setPayment_method(paymentMethod.getId());
        topUpHistory.setStatus(0);
        topUpHistory.setCreated_at(utility.getCurrentTime());
        topUpHistoryMapper.insert(topUpHistory);

        TopUpHistory topUpHistoryLatest = topUpHistoryMapper.findLatestRecordByDateAndUserId(user.getId(),
                8000 + user.getPhone_number());

        System.out.println(timerTask(topUpHistoryLatest.getId()));

        data.put("topup_balance", balanceCatalog.getBalance());
        data.put("payment_type", paymentMethod.getPayment_type());
        data.put("name", paymentMethod.getName());
        data.put("invoice_id", topUpHistoryLatest.getId());
        data.put("token", topUpHistoryLatest.getToken());
        data.put("status", topUpHistoryLatest.getStatus());
        data.put("created_at", topUpHistoryLatest.getCreated_at());

        jsonObject.put("status", 200);
        jsonObject.put("data", data);
        jsonObject.put("message", "success");

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    public String timerTask(long id) {
        System.out.println("TIMER TASK RUN FOR 5 SECOND with id: " + id);

        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on: " + new Date() + "n"
                        + "Thread's name: " + Thread.currentThread().getName());
            }
        };
        System.out.println("RUN");
        Timer timer = new Timer("Timer");
        long delay = 5000L;
        timer.schedule(task, delay);
        return "task has been initialize";
    }
}
