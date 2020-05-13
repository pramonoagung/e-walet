package com.project.ewalet.controller;

import com.project.ewalet.mapper.OtpMapper;
import com.project.ewalet.model.payload.ResendOtpRequest;
import com.project.ewalet.utils.Validation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OtpController {

    @Autowired
    OtpMapper otpMapper;
    @Autowired
    Validation validation;

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpRequest resendOtpRequest) {
        JSONObject jsonObject = new JSONObject();
        if (validation.email(resendOtpRequest.getEmail()) && validation.phoneNumber(resendOtpRequest.getPhone_number())) {
            
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } else {
            jsonObject.put("status", 406);
            jsonObject.put("message", "Either phone number or email is wrong");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
