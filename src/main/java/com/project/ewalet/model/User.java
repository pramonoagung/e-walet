package com.project.ewalet.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 25)
    private String email;
    @Column(length = 60)
    @JsonIgnore
    private String password;
    @Column(length = 25)
    private String first_name;
    @Column(length = 25)
    private String last_name;
    @Column(length = 25)
    private String phone_number;
    @Column(length = 255)
    private String token;
    @Column()
    private int status;
//    @Column
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at = LocalDateTime.now();
}