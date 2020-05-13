package com.project.ewalet.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_balance", indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "user_id")})
@Data
public class UserBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 11, name = "user_id")
    private long user_id;
    @Column(columnDefinition="BIGINT(20)")
    private long balance;
}
