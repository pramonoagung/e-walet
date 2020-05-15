package com.project.ewalet.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;

//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "otp", indexes = {@Index(columnList = "id"), @Index(columnList = "user_id")})
@Data
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 11)
    private long user_id;
    @Column(length = 6)
    private String code;
    @Column(length = 6)
    private boolean status;
    @Column(length = 50)
    private String created_at;
}
