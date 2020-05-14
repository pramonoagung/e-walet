package com.project.ewalet.model.payload;

import lombok.Data;


@Data
public class TopUpHistoryPayload {
    private long id;
    private long user_id;
    private long topup_balance;
    private String token;
    private int payment_type;
    private String name;
    private int status;
    private String path;
    private String created_at;
}
