package com.project.ewalet.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "balance_catalog", indexes = @Index(columnList = "id"))
@Data
public class BalanceCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 25)
    private String code;
    private long balance;
}
