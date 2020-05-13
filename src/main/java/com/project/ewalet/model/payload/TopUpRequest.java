package com.project.ewalet.model.payload;

import lombok.Data;

@Data
public class TopUpRequest {
    private String phone_number, code;
    private int payment_method_id;
}
