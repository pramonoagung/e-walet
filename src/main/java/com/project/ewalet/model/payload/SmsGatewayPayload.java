package com.project.ewalet.model.payload;

import lombok.Data;

@Data
public class SmsGatewayPayload {
    private String phone_number, otp_code, message, status;
}
