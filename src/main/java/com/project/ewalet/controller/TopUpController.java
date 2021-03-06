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

import javax.validation.Valid;
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
    public ResponseEntity<?> topup(@Valid @RequestBody TopUpRequest topUpRequest) {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        BalanceCatalog balanceCatalog = balanceCatalogMapper.findByCode(topUpRequest.getCode());
        PaymentMethod paymentMethod = paymentMethodMapper.getById(topUpRequest.getPayment_method_id());
        User user = userMapper.findByPhoneNumber(topUpRequest.getPhone_number());
        if (balanceCatalog == null) {
            jsonObject.put("status", 404);
            jsonObject.put("message", "balance catalog not found");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        } else if (paymentMethod == null) {
            jsonObject.put("status", 404);
            jsonObject.put("message", "payment method not found");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        } else if (user == null) {
            jsonObject.put("status", 404);
            jsonObject.put("message", "User not found");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        } else {

            //record to db
            TopUpHistory topUpHistory = new TopUpHistory();
            topUpHistory.setUser_id(user.getId());
            topUpHistory.setTopup_balance(balanceCatalog.getBalance());
            topUpHistory.setToken(8000 + user.getPhone_number());
            topUpHistory.setPayment_method(paymentMethod.getId());
            topUpHistory.setStatus(0);
            topUpHistory.setCreated_at(utility.getCurrentTime());
            topUpHistory.setFile_upload_id(0);
            topUpHistoryMapper.insert(topUpHistory);
            System.out.println(topUpHistory.getId());

            TopUpHistory topUpHistoryLatest = topUpHistoryMapper.getTopUpHistoryById(topUpHistory.getId());

            System.out.println(paymentExpirationTask(topUpHistoryLatest.getId()));

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
    }

    public String paymentExpirationTask(long id) {
        System.out.println("Timer task run for 5 minutes with payment id: " + id + " on " + new Date());

        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on: " + new Date() + "n"
                        + "Thread's name: " + Thread.currentThread().getName());
                if (topUpHistoryMapper.getTopUpHistoryById(Long.parseLong(Thread.currentThread().getName())).getStatus() != 1) {
                    topUpHistoryMapper.updateStatusById(2, Long.parseLong(Thread.currentThread().getName()));
                }
            }
        };
        Timer timer = new Timer("" + id);
        long hourInMillis = 1000 * 60 * 60;
        long delay = hourInMillis / 12;
        timer.schedule(task, delay);
        return "expiration task has been initialize";
    }
}
