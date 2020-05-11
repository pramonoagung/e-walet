package com.project.ewalet.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "balance_catalog")
@Data
public class BalanceCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;
    private long balance;
}
