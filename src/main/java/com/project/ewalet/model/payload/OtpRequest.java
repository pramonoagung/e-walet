package com.project.ewalet.model.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OtpRequest {
    @NotNull(message = "Phone number must not be null")
    private String phone_number;
    @NotNull(message = "Otp code must not be null")
    private String otp_code;
}
