package com.project.ewalet.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "files",indexes = {@Index(columnList = "id"), @Index(columnList = "user_id")})
@Data
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 11)
    private long user_id;
    @Column
    private int file_type;
    @Column(length = 255)
    private String path;
    @Column(length = 255)
    private String file_name;
}
