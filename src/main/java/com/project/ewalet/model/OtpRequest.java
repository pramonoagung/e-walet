package com.project.ewalet.model;

import lombok.Data;

@Data
public class OtpRequest {
    private String phone_number;
    private int otp_code;
}
