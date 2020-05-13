package com.project.ewalet.service;

import com.project.ewalet.model.payload.SmsGatewayPayload;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;
    @Value("${twilio.auth.token}")
    private String authToken;
    @Value("${twilio.phonenumber.trial}")
    private String trialPhoneNumber;

    SmsGatewayPayload sendSms(String phoneNumber, String otpCode) {
        SmsGatewayPayload smsResponse = new SmsGatewayPayload();
        smsResponse.setPhone_number(phoneNumber);
        smsResponse.setOtp_code(otpCode);
        String body = "Your E-Walet verification code is " + otpCode;
        smsResponse.setMessage(body);
        Twilio.init(accountSid, authToken);
        Message message = Message
                .creator(new PhoneNumber("+" + phoneNumber),
                        new PhoneNumber(trialPhoneNumber),
                        body)
                .create();
        System.out.println(message.getStatus());
        smsResponse.setStatus(message.getStatus().toString());
        return smsResponse;
    }
}
