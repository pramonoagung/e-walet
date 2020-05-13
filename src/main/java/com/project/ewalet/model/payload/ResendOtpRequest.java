package com.project.ewalet.model.payload;

import lombok.Data;

@Data
public class ResendOtpRequest {
    private String phone_number, email;
}
