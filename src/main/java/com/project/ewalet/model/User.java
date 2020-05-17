package com.project.ewalet.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
@Table(name = "user", indexes = @Index(columnList = "id"))
@Data @NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50)
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
    private int status;
    private String created_at = LocalDateTime.now().toString();
}