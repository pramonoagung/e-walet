package com.project.ewalet.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "topup_history", indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "user_id"),
        @Index(columnList = "payment_method")
})
@Data
public class TopUpHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 11)
    private long user_id;
    @Column(length = 11)
    private long topup_balance;
    @Column(length = 25)
    private String token;
    @Column
    private int payment_method;
    @Column
    private int status;
    @Column
    private String file_path;
    @Column
    private String created_at;
}
