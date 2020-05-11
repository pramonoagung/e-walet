package com.project.ewalet.controller;

import com.project.ewalet.model.BalanceCatalog;
import com.project.ewalet.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PaymentMethodController {

    @Autowired
    PaymentMethodMapper paymentMethodMapper;

    @PostMapping("/get-balance-catalog")
    public ResponseEntity getBalanceCatalog() {

        return new ResponseEntity<BalanceCatalog>(paymentMethodMapper.getAll(), HttpStatus.OK);
    }

}
