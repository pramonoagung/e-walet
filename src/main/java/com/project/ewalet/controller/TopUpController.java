package com.project.ewalet.controller;

import com.project.ewalet.mapper.BalanceCatalogMapper;
import com.project.ewalet.mapper.PaymentMethodMapper;
import com.project.ewalet.mapper.UserBalanceMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.BalanceCatalog;
import com.project.ewalet.model.PaymentMethod;
import com.project.ewalet.model.User;
import com.project.ewalet.model.UserBalance;
import com.project.ewalet.model.payload.TopUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TopUpController {

    @Autowired
    private BalanceCatalogMapper balanceCatalogMapper;
    @Autowired
    private PaymentMethodMapper paymentMethodMapper;
    @Autowired
    private UserBalanceMapper userBalanceMapper;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/topup-balance")
    public ResponseEntity<?> topup(@RequestBody TopUpRequest topUpRequest) {

        BalanceCatalog balanceCatalog = balanceCatalogMapper.findByCode(topUpRequest.getCode());
        PaymentMethod paymentMethod = paymentMethodMapper.findByCode(topUpRequest.getPayment_type());
        User user = userMapper.findByPhoneNumber(topUpRequest.getPhone_number());
        UserBalance userBalance = userBalanceMapper.findByUserId(user.getId());
        long actualBalance = userBalance.getBalance();
        long currentBalance = balanceCatalog.getBalance() + actualBalance;

        //update user balance
        userBalanceMapper.updateUserBalance(currentBalance, user.getId());

        //TODO add to top-up history

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
