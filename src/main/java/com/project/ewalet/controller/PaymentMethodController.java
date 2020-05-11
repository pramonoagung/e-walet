package com.project.ewalet.controller;

import com.project.ewalet.mapper.PaymentMethodMapper;
import com.project.ewalet.model.BalanceCatalog;
import com.project.ewalet.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class PaymentMethodController {

    @Autowired
    PaymentMethodMapper paymentMethodMapper;

    @GetMapping("/get-payment-method")
    public ResponseEntity getBalanceCatalog() {

        return new ResponseEntity<ArrayList<PaymentMethod>>(paymentMethodMapper.getAll(), HttpStatus.OK);
    }

}
