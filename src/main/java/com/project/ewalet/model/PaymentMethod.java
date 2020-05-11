package com.project.ewalet.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "payment_method", indexes = @Index(columnList = "id"))
@Data
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int payment_type;
    @Column(length = 25)
    private String name;
}