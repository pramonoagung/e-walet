package com.project.ewalet.model.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TopUpRequest {
    @NotNull(message = "Phone Number must not be null")
    private String phone_number;
    @NotNull(message = "Balance Code must not be null")
    private String code;
    @NotNull(message = "Payment Method ID must not be null")
    private int payment_method_id;
}
