package com.project.ewalet.model.payload;

import lombok.Data;

@Data
public class OtpRequest {
    private String phone_number;
    private int otp_code;
}