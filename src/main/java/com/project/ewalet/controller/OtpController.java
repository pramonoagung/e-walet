package com.project.ewalet.controller;

import com.project.ewalet.mapper.OtpMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.Otp;
import com.project.ewalet.model.User;
import com.project.ewalet.model.payload.ResendOtpRequest;
import com.project.ewalet.service.rabbitmq.MQPublisher;
import com.project.ewalet.utils.Utility;
import com.project.ewalet.utils.Validation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.Random;

@RestController
@CrossOrigin
public class OtpController {

    @Autowired
    OtpMapper otpMapper;
    @Autowired
    Validation validation;
    @Autowired
    private MQPublisher mqPublisher;
    @Autowired
    private Utility utility;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpRequest resendOtpRequest) {
        JSONObject jsonObject = new JSONObject();
        if (validation.email(resendOtpRequest.getEmail()) && validation.phoneNumber(resendOtpRequest.getPhone_number())) {
            String otpCode = utility.otpCode();
            User user = userMapper.findByEmail(resendOtpRequest.getEmail());
            Otp otp = new Otp();
            otp.setUser_id(user.getId());
            otp.setCode(otpCode);
            otp.setStatus(true);
            otp.setCreated_at(utility.getCurrentTimeOtp());
            otpMapper.save(otp);

            sendSms(resendOtpRequest.getPhone_number(), otpCode);
            sendEmail(resendOtpRequest.getEmail(), otpCode);
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } else {
            jsonObject.put("status", 406);
            jsonObject.put("message", "Either phone number or email is wrong");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    void sendSms(String phoneNumber, String otpCode) {
        JSONObject jsonSendSms = new JSONObject();
        jsonSendSms.put("phoneNumber", phoneNumber);
        jsonSendSms.put("otpCode", otpCode);
        mqPublisher.mqSendSms(jsonSendSms.toString());
    }

    void sendEmail(String toEmail, String otpCode) {
        JSONObject jsonSendEmail = new JSONObject();
        jsonSendEmail.put("toEmail", toEmail);
        jsonSendEmail.put("otpCode", otpCode);
        mqPublisher.mqSendEmail(jsonSendEmail.toString());
    }
}
