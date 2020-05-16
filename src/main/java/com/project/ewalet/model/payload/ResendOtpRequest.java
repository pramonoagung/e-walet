package com.project.ewalet.model.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResendOtpRequest {
    @NotNull(message = "Phone number must not be null")
    private String phone_number;
    @NotNull(message = "Email must not be null")
    private String email;
}
