package com.project.ewalet.model.payload;

import lombok.Data;

@Data
public class logout {
    private String user_id, token;
}
